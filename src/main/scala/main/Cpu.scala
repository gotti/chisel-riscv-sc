package main

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile
import consts.Consts._
import consts.Instructions._
import consts._

class Cpu extends Module {
  var io = IO(new Bundle {
    var Memory = Flipped(new MemoryIo())
    var ShadowStack = Flipped(new ShadowStackIo())
    var regIo = Flipped(new RegisterIo())
    val csrIo = Flipped(new CsrRegisterIo())
    val pc_is_0x44 = Output(Bool())
    val exit = Output(Bool())
  })

  val pc = RegInit(0.U(WORD_LEN.W))
  val privilege_level = RegInit(3.U(2.W))
  io.Memory.InstAddr := pc
  io.pc_is_0x44 := pc===0x44.U
  val inst = io.Memory.InstData

  val rs1_address = inst(19, 15)
  val rs2_address = inst(24, 20)
  val rd_address = inst(11,7)
  io.regIo.rs1_address := rs1_address
  io.regIo.rs2_address := rs2_address
  io.regIo.rd_address := rd_address
  val register_writeback_data = Wire(UInt(WORD_LEN.W))
  io.regIo.rd_writeback := register_writeback_data

  var is_ecall = Mux(inst===ECALL, IS_ECALL, IS_NOT_ECALL)

  val shadowstack_not_met = Wire(Bool())

  io.csrIo.csr_read_address := MuxCase( inst(31,20),
    Array(
      (is_ecall===IS_ECALL) -> consts.CSRs.mtvec.U(CSR_ADDRESS_LEN.W),
      (shadowstack_not_met) -> consts.CSRs.mtvec.U(CSR_ADDRESS_LEN.W),
    )
  )
  io.csrIo.csr_write_address := MuxCase( inst(31,20),
    Array(
      (is_ecall===IS_ECALL) -> consts.CSRs.mcause.U(CSR_ADDRESS_LEN.W),
      (shadowstack_not_met) -> consts.CSRs.mcause.U(CSR_ADDRESS_LEN.W),
    ),
  )
  //val csr_writeback_value = Wire(UInt(WORD_LEN.W))
  //io.csrIo.csr_write_value := csr_writeback_value


  val csr_data = Wire(UInt(CSR_DATA_LEN.W))
  csr_data := io.csrIo.csr_read_value

  val rs1_data = io.regIo.rs1_value
  val rs2_data = io.regIo.rs2_value

  val control = ListLookup(inst,
    List(ALU_ADDSUB, ALU_POS, ALUIN2_REG, RWB_DISABLE, RWB_NONE),
    Array(
      ADD -> List(ALU_ADDSUB, ALU_POS, ALUIN2_REG, RWB_ENABLE, RWB_ALU),
      SUB -> List(ALU_ADDSUB, ALU_NEG, ALUIN2_REG, RWB_ENABLE, RWB_ALU),
      ADDI -> List(ALU_ADDSUB, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_ALU),
      AND -> List(ALU_AND, ALU_POS, ALUIN2_REG, RWB_ENABLE, RWB_ALU),
      ANDI -> List(ALU_AND, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_ALU),
      XOR -> List(ALU_XOR, ALU_POS, ALUIN2_REG, RWB_ENABLE, RWB_ALU),
      XORI -> List(ALU_XOR, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_ALU),
      OR -> List(ALU_OR, ALU_POS, ALUIN2_REG, RWB_ENABLE, RWB_ALU),
      ORI -> List(ALU_OR, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_ALU),
      SLL -> List(ALU_SLL, ALU_POS, ALUIN2_REG, RWB_ENABLE, RWB_ALU),
      SLLI -> List(ALU_SLL, ALU_POS, ALUIN2_SHAMT, RWB_ENABLE, RWB_ALU),
      LUI -> List(ALU_PASS2, ALU_POS, ALUIN2_IMM_U, RWB_ENABLE, RWB_ALU),
      LB -> List(ALU_ADDSUB, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_MEM),
      LBU -> List(ALU_ADDSUB, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_MEM),
      LH -> List(ALU_ADDSUB, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_MEM),
      LHU -> List(ALU_ADDSUB, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_MEM),
      LW -> List(ALU_ADDSUB, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_MEM),
      SB -> List(ALU_ADDSUB, ALU_POS, ALUIN2_IMM_S, RWB_DISABLE, RWB_ALU),
      SH -> List(ALU_ADDSUB, ALU_POS, ALUIN2_IMM_S, RWB_DISABLE, RWB_ALU),
      SW -> List(ALU_ADDSUB, ALU_POS, ALUIN2_IMM_S, RWB_DISABLE, RWB_ALU),
      SLT -> List(ALU_SLTI, ALU_POS, ALUIN2_REG, RWB_ENABLE, RWB_ALU),
      SLTI -> List(ALU_SLTI, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_ALU),
      SLTU -> List(ALU_SLTIU, ALU_POS, ALUIN2_REG, RWB_ENABLE, RWB_ALU),
      SLTIU -> List(ALU_SLTIU, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_ALU),
      SRL -> List(ALU_SRL, ALU_POS, ALUIN2_REG, RWB_ENABLE, RWB_ALU),
      SRLI -> List(ALU_SRL, ALU_POS, ALUIN2_SHAMT, RWB_ENABLE, RWB_ALU),
      SRA -> List(ALU_SRL, ALU_NEG, ALUIN2_REG, RWB_ENABLE, RWB_ALU),
      SRAI -> List(ALU_SRL, ALU_NEG, ALUIN2_SHAMT, RWB_ENABLE, RWB_ALU), //shamt[5]==1のときなぞ
      BEQ -> List(ALU_ADDSUB, ALU_NEG, ALUIN2_REG, RWB_DISABLE, RWB_NONE),
      BNE -> List(ALU_ADDSUB, ALU_NEG, ALUIN2_REG, RWB_DISABLE, RWB_NONE),
      BLT -> List(ALU_SLTI, ALU_POS, ALUIN2_REG, RWB_DISABLE, RWB_NONE),
      BGE -> List(ALU_SLTI, ALU_POS, ALUIN2_REG, RWB_DISABLE, RWB_NONE),
      BLTU -> List(ALU_SLTIU, ALU_POS, ALUIN2_REG, RWB_DISABLE, RWB_NONE),
      BGEU -> List(ALU_SLTIU, ALU_POS, ALUIN2_REG, RWB_DISABLE, RWB_NONE),
      AUIPC -> List(ALU_ADDSUB, ALU_POS, ALUIN2_IMM_U, RWB_ENABLE, RWB_ALU),
      CSRRS -> List(ALU_OR, ALU_POS, ALUIN2_CSR, RWB_ENABLE, RWB_CSR),
      CSRRW -> List(ALU_PASS1, ALU_POS, ALUIN2_CSR, RWB_ENABLE, RWB_CSR),
      CSRRC -> List(ALU_AND, ALU_POS, ALUIN2_CSR, RWB_ENABLE, RWB_CSR),
      CSRRWI -> List(ALU_PASS1, ALU_POS, ALUIN2_CSR, RWB_ENABLE, RWB_CSR),
      CSRRCI -> List(ALU_AND, ALU_POS, ALUIN2_CSR, RWB_ENABLE, RWB_CSR),
      CSRRSI -> List(ALU_OR, ALU_POS, ALUIN2_CSR, RWB_ENABLE, RWB_CSR),
      JAL -> List(ALU_PASS1, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_PC),
      JALR -> List(ALU_PASS1, ALU_POS, ALUIN2_IMM_I, RWB_ENABLE, RWB_PC),
      )
    )


  val alu_control :: alu_isneg :: aluin2_control :: rwb_isenable :: rwb_control :: Nil = control

  val memory_access_control = ListLookup(inst,
    List(DATAMEM_WE_DISABLE, DATAMEM_BYTEENABLE_0, LOAD_E_DISABLE),
    Array(
      LB -> List(DATAMEM_WE_DISABLE, DATAMEM_BYTEENABLE_1, LOAD_E_ENABLE),
      LBU -> List(DATAMEM_WE_DISABLE, DATAMEM_BYTEENABLE_1, LOAD_E_DISABLE),
      LH -> List(DATAMEM_WE_DISABLE, DATAMEM_BYTEENABLE_2, LOAD_E_ENABLE),
      LHU -> List(DATAMEM_WE_DISABLE, DATAMEM_BYTEENABLE_2, LOAD_E_DISABLE),
      LW -> List(DATAMEM_WE_DISABLE, DATAMEM_BYTEENABLE_4, LOAD_E_DISABLE),
      SB -> List(DATAMEM_WE_ENABLE, DATAMEM_BYTEENABLE_1, LOAD_E_DISABLE),
      SH -> List(DATAMEM_WE_ENABLE, DATAMEM_BYTEENABLE_2, LOAD_E_DISABLE),
      SW -> List(DATAMEM_WE_ENABLE, DATAMEM_BYTEENABLE_4, LOAD_E_DISABLE),
      )
    )

  val datamem_we :: datamem_byteenable :: datamem_signextend :: Nil = memory_access_control

  val branch_control = ListLookup(inst,
    List(IS_NOT_BRANCH, BRANCH_DIRECT, BRANCH_MODE_EQ),
    Array(
      BEQ -> List(IS_BRANCH, BRANCH_DIRECT, BRANCH_MODE_EQ),
      BNE -> List(IS_BRANCH, BRANCH_INVERSE, BRANCH_MODE_EQ),
      BLT -> List(IS_BRANCH, BRANCH_DIRECT, BRANCH_MODE_POS),
      BGE -> List(IS_BRANCH, BRANCH_INVERSE, BRANCH_MODE_POS),
      BLTU -> List(IS_BRANCH, BRANCH_DIRECT, BRANCH_MODE_POS),
      BGEU -> List(IS_BRANCH, BRANCH_INVERSE, BRANCH_MODE_POS),
    )
  )

  val is_branch :: branch_isinv :: branch_mode :: Nil = branch_control

  val imm_i = inst(31, 20)
  val imm_i_e = Cat(Fill(20, imm_i(11)), imm_i)
  val imm_s = Cat(inst(31, 25), inst(11, 7))
  val imm_s_e = Cat(Fill(20, imm_s(11)), imm_s)
  val imm_b = Cat(inst(31), inst(7), inst(30, 25), inst(11, 8))
  val imm_b_e = Cat(Fill(19, imm_b(11)), imm_b, 0.U(1.U))
  val imm_j = Cat(inst(31), inst(19, 12), inst(20), inst(30, 21))
  val imm_j_e = Cat(Fill(11, imm_j(19)), imm_j, 0.U(1.U))
  val imm_u = inst(31,12)
  val imm_u_shifted = Cat(imm_u, Fill(12, 0.U))
  val imm_z = inst(19,15)
  val imm_z_uext = Cat(Fill(27, 0.U), imm_z)
  val imm_z_uext_f = ~imm_z_uext

  val imm_shamt = inst(25,20)

  val csr_mask = MuxCase( 0.U(WORD_LEN.W),
    Array(
      (inst===CSRRW) -> rs1_data,
      (inst===CSRRC) -> rs1_data,
      (inst===CSRRS) -> rs1_data,
      (inst===CSRRWI) -> imm_z_uext,
      (inst===CSRRCI) -> imm_z_uext,
      (inst===CSRRSI) -> imm_z_uext,
      )
    )

  val csr_mask_f = MuxCase(csr_mask,
    Array(
      (inst===CSRRC) -> ~csr_mask,
      (inst===CSRRCI) -> ~csr_mask,
      )
    )

  val csr_control = ListLookup(inst,
    List(ALUIN1_SELECT_RS1, CSR_WB_DISABLE),
    Array(
      AUIPC -> List(ALUIN1_SELECT_PC, CSR_WB_DISABLE),
      CSRRW -> List(ALUIN1_SELECT_CSRMASK, CSR_WB_ENABLE),
      CSRRS -> List(ALUIN1_SELECT_CSRMASK, CSR_WB_ENABLE),
      CSRRC -> List(ALUIN1_SELECT_CSRMASK, CSR_WB_ENABLE),
      CSRRWI -> List(ALUIN1_SELECT_CSRMASK, CSR_WB_ENABLE),
      CSRRCI -> List(ALUIN1_SELECT_CSRMASK, CSR_WB_ENABLE),
      CSRRSI -> List(ALUIN1_SELECT_CSRMASK, CSR_WB_ENABLE),
      ECALL -> List(ALUIN1_SELECT_RS1, CSR_WB_ENABLE),
    ))

  val aluin1_select :: csr_wb_isenable :: Nil = csr_control
  val aluin1 = MuxCase(0.U(WORD_LEN.W), Seq(
    (aluin1_select === ALUIN1_SELECT_RS1) -> rs1_data,
    (aluin1_select === ALUIN1_SELECT_CSRMASK) -> csr_mask_f,
    (aluin1_select === ALUIN1_SELECT_PC) -> pc,
    ))

  val aluin2 = MuxCase(0.U(WORD_LEN.W), Seq(
    (aluin2_control === ALUIN2_REG) -> rs2_data,
    (aluin2_control === ALUIN2_IMM_I) -> imm_i_e,
    (aluin2_control === ALUIN2_IMM_S) -> imm_s_e,
    (aluin2_control === ALUIN2_IMM_J) -> imm_j_e,
    (aluin2_control === ALUIN2_IMM_U) -> imm_u_shifted,
    (aluin2_control === ALUIN2_CSR) -> csr_data,
    (aluin2_control === ALUIN2_SHAMT) -> imm_shamt,
    ))

  val alu_out = Wire(UInt(WORD_LEN.W))

  alu_out := MuxCase(0.U(WORD_LEN.W), Seq(
    (alu_control === ALU_ADDSUB) -> (Mux(alu_isneg===ALU_POS,  aluin1+aluin2 , aluin1-aluin2 )),
    (alu_control === ALU_SLL) -> (aluin1<<aluin2(4,0)),
    (alu_control === ALU_SLTI) -> (aluin1.asSInt()<aluin2.asSInt()).asUInt(),
    (alu_control === ALU_SLTIU) -> (aluin1 < aluin2).asUInt(),
    (alu_control === ALU_XOR) -> (aluin1 ^ aluin2),
    (alu_control === ALU_SRL) -> (Mux(alu_isneg===ALU_POS, (aluin1 >> aluin2(4,0)).asUInt(), ((aluin1).asSInt() >> aluin2).asUInt() )),
    (alu_control === ALU_OR) -> (aluin1 | aluin2),
    (alu_control === ALU_AND) -> (aluin1 & aluin2),
    (alu_control === ALU_PASS1) -> (aluin1),
    (alu_control === ALU_PASS2) -> (aluin2),
    ))

  io.csrIo.csr_write_value := MuxCase(alu_out, 
    Array(
      (is_ecall===IS_ECALL) -> 11.U,
      (shadowstack_not_met) -> 0x103.U,
    )
  )

  io.csrIo.csr_write_enable := (csr_wb_isenable | shadowstack_not_met)

  val branch_target = Wire(UInt(WORD_LEN.W))
  branch_target := pc + imm_b_e

  val is_branch_ok = Wire(Bool())
  is_branch_ok := MuxCase(false.B, Seq(
    (branch_mode === BRANCH_MODE_EQ) -> (Mux(branch_isinv===BRANCH_DIRECT, (alu_out===0.U(WORD_LEN.W)), !(alu_out===0.U(WORD_LEN.W)))),
    (branch_mode === BRANCH_MODE_POS) -> (Mux(branch_isinv===BRANCH_DIRECT, alu_out(0), !alu_out(0))),
  ))


  val shadowstack_push_enable = ((inst===JALR || inst===JAL) && (rd_address === 1.U))
  val shadowstack_pop_enable = ((inst===JALR) && (rd_address===0.U && rs1_address===1.U && imm_i===0.U))

  val pc_plus4 = pc+4.U
  val jalr_pc = ((rs1_data+imm_i_e) & ~1.U(WORD_LEN.W))
  val next_pc = MuxCase(pc_plus4, Seq(
      (shadowstack_not_met) -> (csr_data),
      (pc === 0x999.U) -> 0x999.U(WORD_LEN.W),
      (inst === JAL) -> (pc+imm_j_e),
      (inst === JALR) -> jalr_pc,
      (is_branch === IS_BRANCH) -> (Mux(is_branch_ok===1.U,branch_target, pc_plus4)),
      (is_ecall === IS_ECALL) -> (csr_data),
    ))
  pc := next_pc

  val dataMem_e = MuxCase(0.U(WORD_LEN.W), Seq(
    (datamem_byteenable === DATAMEM_BYTEENABLE_0) -> 0.U(32.U),
    (datamem_byteenable === DATAMEM_BYTEENABLE_1) -> Cat(Fill(24, io.Memory.ReadData(7)), io.Memory.ReadData(7,0)),
    (datamem_byteenable === DATAMEM_BYTEENABLE_2) -> Cat(Fill(16, io.Memory.ReadData(15)), io.Memory.ReadData(15,0)),
    (datamem_byteenable === DATAMEM_BYTEENABLE_4) -> io.Memory.ReadData,
    )
  )

  register_writeback_data := MuxCase(0.U(WORD_LEN.W), Seq(
    (rwb_control === RWB_ALU) -> alu_out,
    (rwb_control === RWB_PC) -> (pc+4.U(WORD_LEN.W)),
    (rwb_control === RWB_CSR) -> csr_data,
    (rwb_control === RWB_MEM) -> Mux(datamem_signextend===LOAD_E_ENABLE, dataMem_e, io.Memory.ReadData),
  ))
  io.regIo.rd_writeControl := rwb_isenable

  io.Memory.ReadAddr := alu_out
  io.Memory.WriteAddr := alu_out
  io.Memory.WriteData := rs2_data
  io.Memory.byte_enable := datamem_byteenable
  io.Memory.write_enable := datamem_we


  io.ShadowStack.push_enable := shadowstack_push_enable
  io.ShadowStack.pop_enable := shadowstack_pop_enable
  io.ShadowStack.writeData := pc+4.U

  shadowstack_not_met := (inst === JALR && shadowstack_pop_enable && !(io.ShadowStack.readData === jalr_pc))

  io.exit := (inst===HALT)
  printf(p"io.pc      : 0x${Hexadecimal(pc)}\n")
  printf(p"inst       : 0x${Hexadecimal(inst)}\n")
  printf(p"rs1_addr   : $rs1_address\n")
  printf(p"rs2_addr   : $rs2_address\n")
  printf(p"wb_addr    : $rd_address\n")
  printf(p"writeback_c: $rwb_control\n")
  printf(p"rs1_data   : 0x${Hexadecimal(rs1_data)}\n")
  printf(p"rs2_data   : 0x${Hexadecimal(rs2_data)}\n")
  printf(p"aluin1     : 0x${Hexadecimal(aluin1)}\n")
  printf(p"aluin2     : 0x${Hexadecimal(aluin2)}\n")
  printf(p"alu_out    : ${alu_out}\n")
  printf(p"imm_i      : 0x${Hexadecimal(imm_i_e)}\n")
  printf(p"alu_isneg  : $alu_isneg\n")
  printf(p"alucontrol : $alu_control\n")
  printf(p"wb_data    : 0x${Hexadecimal(register_writeback_data)}\n")
  printf(p"branch_mode: $branch_mode\n")
  printf(p"is_branch  : $is_branch\n")
  printf(p"branch_inv : $branch_isinv\n")
  printf(p"branch_ok  : $is_branch_ok\n")
  printf(p"ss_not_met : $shadowstack_not_met\n")
  printf("---------\n")
}
