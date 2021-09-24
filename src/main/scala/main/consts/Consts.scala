/* Automatically generated by parse_opcodes */
package consts

import chisel3._
import chisel3.util._

object Consts {
  val WORD_LEN = 32
  val WORD_ADDRESS_LEN = 5

  val ALUIN2_SWITCH_LEN = 3
  val ALUIN2_REG = 0.U(ALUIN2_SWITCH_LEN.W)
  val ALUIN2_IMM_I = 1.U(ALUIN2_SWITCH_LEN.W)
  val ALUIN2_IMM_S = 2.U(ALUIN2_SWITCH_LEN.W)
  val ALUIN2_IMM_J = 3.U(ALUIN2_SWITCH_LEN.W)
  val ALUIN2_IMM_U = 4.U(ALUIN2_SWITCH_LEN.W)
  val ALUIN2_CSR   = 5.U(ALUIN2_SWITCH_LEN.W)
  val ALUIN2_SHAMT = 6.U(ALUIN2_SWITCH_LEN.W)

  val ALU_CONTROL_LEN = 4
  val ALU_ADDSUB = 0.U(ALU_CONTROL_LEN.W)
  val ALU_SLL = 1.U(ALU_CONTROL_LEN.W)
  val ALU_SLTI = 2.U(ALU_CONTROL_LEN.W)
  val ALU_SLTIU = 3.U(ALU_CONTROL_LEN.W)
  val ALU_XOR = 4.U(ALU_CONTROL_LEN.W)
  val ALU_SRL = 5.U(ALU_CONTROL_LEN.W)
  val ALU_OR = 6.U(ALU_CONTROL_LEN.W)
  val ALU_AND = 7.U(ALU_CONTROL_LEN.W)
  val ALU_PASS1 = 8.U(ALU_CONTROL_LEN.W)
  val ALU_PASS2 = 9.U(ALU_CONTROL_LEN.W)

  val ALU_NEG_LEN = 1
  val ALU_POS = 0.U(ALU_NEG_LEN.W)
  val ALU_NEG = 1.U(ALU_NEG_LEN.W)

  val BRANCH_INV_LEN = 1
  val BRANCH_DIRECT = 0.U(BRANCH_INV_LEN.W)
  val BRANCH_INVERSE = 1.U(BRANCH_INV_LEN.W)

  val IS_BRANCH_LEN = 1
  val IS_NOT_BRANCH = 0.U(IS_BRANCH_LEN.W)
  val IS_BRANCH = 1.U(IS_BRANCH_LEN.W)

  val BRANCH_MODE_LEN = 3
  val BRANCH_MODE_EQ = 0.U(BRANCH_MODE_LEN.W)
  val BRANCH_MODE_POS = 1.U(BRANCH_MODE_LEN.W)

  val NEXT_PC_SELECT_LEN = 2
  val NEXT_PC_PLUS4 = 0.U(NEXT_PC_SELECT_LEN.W)
  val NEXT_PC_JAL = 1.U(NEXT_PC_SELECT_LEN.W)
  val NEXT_PC_JALR = 2.U(NEXT_PC_SELECT_LEN.W)
  val NEXT_PC_BRANCH = 3.U(NEXT_PC_SELECT_LEN.W)

  val REGISTER_WRITEBACK_SELECT_LEN = 3
  val RWB_NONE = 0.U(REGISTER_WRITEBACK_SELECT_LEN.W)
  val RWB_ALU = 1.U(REGISTER_WRITEBACK_SELECT_LEN.W)
  val RWB_PC = 2.U(REGISTER_WRITEBACK_SELECT_LEN.W)
  val RWB_CSR = 3.U(REGISTER_WRITEBACK_SELECT_LEN.W)
  val RWB_MEM = 4.U(REGISTER_WRITEBACK_SELECT_LEN.W)

  val RWB_IS_ENABLE_LEN = 1
  val RWB_DISABLE = 0.U(RWB_IS_ENABLE_LEN.W)
  val RWB_ENABLE = 1.U(RWB_IS_ENABLE_LEN.W)

  val CSR_ADDRESS_LEN = 12
  val CSR_DATA_LEN = 32

  val ALUIN1_SELECT_LEN = 2
  val ALUIN1_SELECT_RS1 = 0.U(ALUIN1_SELECT_LEN.W)
  val ALUIN1_SELECT_IMMZ = 1.U(ALUIN1_SELECT_LEN.W)
  val ALUIN1_SELECT_PC = 2.U(ALUIN1_SELECT_LEN.W)
  //val ALUIN1_SELECT_CSR = 2.U(ALUIN1_SELECT_LEN.W)

  val CSR_WB_E_LEN = 1
  val CSR_WB_DISABLE = 0.U(CSR_WB_E_LEN.W)
  val CSR_WB_ENABLE = 1.U(CSR_WB_E_LEN.W)

  val IS_ECALL_LEN = 1
  val IS_NOT_ECALL = 0.U(IS_ECALL_LEN.W)
  val IS_ECALL = 1.U(IS_ECALL_LEN.W)

  val DATAMEM_BYTEENABLE_LEN = 3
  val DATAMEM_BYTEENABLE_0 = 0.U(DATAMEM_BYTEENABLE_LEN.W)
  val DATAMEM_BYTEENABLE_1 = 1.U(DATAMEM_BYTEENABLE_LEN.W)
  val DATAMEM_BYTEENABLE_2 = 2.U(DATAMEM_BYTEENABLE_LEN.W)
  val DATAMEM_BYTEENABLE_4 = 4.U(DATAMEM_BYTEENABLE_LEN.W)

  val DATAMEM_WE_LEN = 1
  val DATAMEM_WE_DISABLE = 0.U(DATAMEM_WE_LEN.W)
  val DATAMEM_WE_ENABLE = 1.U(DATAMEM_WE_LEN.W)

  val LOAD_E_ENABLE_LEN = 1
  val LOAD_E_DISABLE = 0.U(LOAD_E_ENABLE_LEN.W)
  val LOAD_E_ENABLE = 1.U(LOAD_E_ENABLE_LEN.W)
}

object Instructions {
  val BEQ                = BitPat("b?????????????????000?????1100011")
  val BNE                = BitPat("b?????????????????001?????1100011")
  val BLT                = BitPat("b?????????????????100?????1100011")
  val BGE                = BitPat("b?????????????????101?????1100011")
  val BLTU               = BitPat("b?????????????????110?????1100011")
  val BGEU               = BitPat("b?????????????????111?????1100011")
  val JALR               = BitPat("b?????????????????000?????1100111")
  val JAL                = BitPat("b?????????????????????????1101111")
  val LUI                = BitPat("b?????????????????????????0110111")
  val AUIPC              = BitPat("b?????????????????????????0010111")
  val ADDI               = BitPat("b?????????????????000?????0010011")
  val SLLI               = BitPat("b0000000??????????001?????0010011")
  val SLTI               = BitPat("b?????????????????010?????0010011")
  val SLTIU              = BitPat("b?????????????????011?????0010011")
  val XORI               = BitPat("b?????????????????100?????0010011")
  val ORI                = BitPat("b?????????????????110?????0010011")
  val ANDI               = BitPat("b?????????????????111?????0010011")
  val SRLI               = BitPat("b0000000??????????101?????0010011")
  val SRAI               = BitPat("b0100000??????????101?????0010011")
  val ADD                = BitPat("b0000000??????????000?????0110011")
  val SUB                = BitPat("b0100000??????????000?????0110011")
  val SLL                = BitPat("b0000000??????????001?????0110011")
  val SLT                = BitPat("b0000000??????????010?????0110011")
  val SLTU               = BitPat("b0000000??????????011?????0110011")
  val XOR                = BitPat("b0000000??????????100?????0110011")
  val SRL                = BitPat("b0000000??????????101?????0110011")
  val SRA                = BitPat("b0100000??????????101?????0110011")
  val OR                 = BitPat("b0000000??????????110?????0110011")
  val AND                = BitPat("b0000000??????????111?????0110011")
  val LB                 = BitPat("b?????????????????000?????0000011")
  val LH                 = BitPat("b?????????????????001?????0000011")
  val LW                 = BitPat("b?????????????????010?????0000011")
  val LBU                = BitPat("b?????????????????100?????0000011")
  val LHU                = BitPat("b?????????????????101?????0000011")
  val SB                 = BitPat("b?????????????????000?????0100011")
  val SH                 = BitPat("b?????????????????001?????0100011")
  val SW                 = BitPat("b?????????????????010?????0100011")
  val FENCE              = BitPat("b?????????????????000?????0001111")
  val FENCE_I            = BitPat("b?????????????????001?????0001111")
  val ECALL              = BitPat("b00000000000000000000000001110011")
  val EBREAK             = BitPat("b00000000000100000000000001110011")
  val SRET               = BitPat("b00010000001000000000000001110011")
  val MRET               = BitPat("b00110000001000000000000001110011")
  val DRET               = BitPat("b01111011001000000000000001110011")
  val SFENCE_VMA         = BitPat("b0001001??????????000000001110011")
  val WFI                = BitPat("b00010000010100000000000001110011")
  val CSRRW              = BitPat("b?????????????????001?????1110011")
  val CSRRS              = BitPat("b?????????????????010?????1110011")
  val CSRRC              = BitPat("b?????????????????011?????1110011")
  val CSRRWI             = BitPat("b?????????????????101?????1110011")
  val CSRRSI             = BitPat("b?????????????????110?????1110011")
  val CSRRCI             = BitPat("b?????????????????111?????1110011")
  val HALT               = BitPat("b00000000000000000000000000000000")
}
object Causes {
  val misaligned_fetch = 0x0
  val fetch_access = 0x1
  val illegal_instruction = 0x2
  val breakpoint = 0x3
  val misaligned_load = 0x4
  val load_access = 0x5
  val misaligned_store = 0x6
  val store_access = 0x7
  val user_ecall = 0x8
  val supervisor_ecall = 0x9
  val virtual_supervisor_ecall = 0xa
  val machine_ecall = 0xb
  val fetch_page_fault = 0xc
  val load_page_fault = 0xd
  val store_page_fault = 0xf
  val fetch_guest_page_fault = 0x14
  val load_guest_page_fault = 0x15
  val virtual_instruction = 0x16
  val store_guest_page_fault = 0x17
  val shadowstack_mismatch = 0x103
  val all = {
    val res = collection.mutable.ArrayBuffer[Int]()
    res += misaligned_fetch
    res += fetch_access
    res += illegal_instruction
    res += breakpoint
    res += misaligned_load
    res += load_access
    res += misaligned_store
    res += store_access
    res += user_ecall
    res += supervisor_ecall
    res += virtual_supervisor_ecall
    res += machine_ecall
    res += fetch_page_fault
    res += load_page_fault
    res += store_page_fault
    res += fetch_guest_page_fault
    res += load_guest_page_fault
    res += virtual_instruction
    res += store_guest_page_fault
    res.toArray
  }
}
object CSRs {
  val fflags = 0x1
  val frm = 0x2
  val fcsr = 0x3
  val vstart = 0x8
  val vxsat = 0x9
  val vxrm = 0xa
  val vcsr = 0xf
  val cycle = 0xc00
  val time = 0xc01
  val instret = 0xc02
  val hpmcounter3 = 0xc03
  val hpmcounter4 = 0xc04
  val hpmcounter5 = 0xc05
  val hpmcounter6 = 0xc06
  val hpmcounter7 = 0xc07
  val hpmcounter8 = 0xc08
  val hpmcounter9 = 0xc09
  val hpmcounter10 = 0xc0a
  val hpmcounter11 = 0xc0b
  val hpmcounter12 = 0xc0c
  val hpmcounter13 = 0xc0d
  val hpmcounter14 = 0xc0e
  val hpmcounter15 = 0xc0f
  val hpmcounter16 = 0xc10
  val hpmcounter17 = 0xc11
  val hpmcounter18 = 0xc12
  val hpmcounter19 = 0xc13
  val hpmcounter20 = 0xc14
  val hpmcounter21 = 0xc15
  val hpmcounter22 = 0xc16
  val hpmcounter23 = 0xc17
  val hpmcounter24 = 0xc18
  val hpmcounter25 = 0xc19
  val hpmcounter26 = 0xc1a
  val hpmcounter27 = 0xc1b
  val hpmcounter28 = 0xc1c
  val hpmcounter29 = 0xc1d
  val hpmcounter30 = 0xc1e
  val hpmcounter31 = 0xc1f
  val vl = 0xc20
  val vtype = 0xc21
  val vlenb = 0xc22
  val sstatus = 0x100
  val sedeleg = 0x102
  val sideleg = 0x103
  val sie = 0x104
  val stvec = 0x105
  val scounteren = 0x106
  val sscratch = 0x140
  val sepc = 0x141
  val scause = 0x142
  val stval = 0x143
  val sip = 0x144
  val satp = 0x180
  val vsstatus = 0x200
  val vsie = 0x204
  val vstvec = 0x205
  val vsscratch = 0x240
  val vsepc = 0x241
  val vscause = 0x242
  val vstval = 0x243
  val vsip = 0x244
  val vsatp = 0x280
  val hstatus = 0x600
  val hedeleg = 0x602
  val hideleg = 0x603
  val hie = 0x604
  val htimedelta = 0x605
  val hcounteren = 0x606
  val hgeie = 0x607
  val htval = 0x643
  val hip = 0x644
  val hvip = 0x645
  val htinst = 0x64a
  val hgatp = 0x680
  val hgeip = 0xe12
  val utvt = 0x7
  val unxti = 0x45
  val uintstatus = 0x46
  val uscratchcsw = 0x48
  val uscratchcswl = 0x49
  val stvt = 0x107
  val snxti = 0x145
  val sintstatus = 0x146
  val sscratchcsw = 0x148
  val sscratchcswl = 0x149
  val mtvt = 0x307
  val mnxti = 0x345
  val mintstatus = 0x346
  val mscratchcsw = 0x348
  val mscratchcswl = 0x349
  val mstatus = 0x300
  val misa = 0x301
  val medeleg = 0x302
  val mideleg = 0x303
  val mie = 0x304
  val mtvec = 0x305
  val mcounteren = 0x306
  val mcountinhibit = 0x320
  val mscratch = 0x340
  val mepc = 0x341
  val mcause = 0x342
  val mtval = 0x343
  val mip = 0x344
  val mtinst = 0x34a
  val mtval2 = 0x34b
  val pmpcfg0 = 0x3a0
  val pmpcfg1 = 0x3a1
  val pmpcfg2 = 0x3a2
  val pmpcfg3 = 0x3a3
  val pmpaddr0 = 0x3b0
  val pmpaddr1 = 0x3b1
  val pmpaddr2 = 0x3b2
  val pmpaddr3 = 0x3b3
  val pmpaddr4 = 0x3b4
  val pmpaddr5 = 0x3b5
  val pmpaddr6 = 0x3b6
  val pmpaddr7 = 0x3b7
  val pmpaddr8 = 0x3b8
  val pmpaddr9 = 0x3b9
  val pmpaddr10 = 0x3ba
  val pmpaddr11 = 0x3bb
  val pmpaddr12 = 0x3bc
  val pmpaddr13 = 0x3bd
  val pmpaddr14 = 0x3be
  val pmpaddr15 = 0x3bf
  val tselect = 0x7a0
  val tdata1 = 0x7a1
  val tdata2 = 0x7a2
  val tdata3 = 0x7a3
  val tinfo = 0x7a4
  val tcontrol = 0x7a5
  val mcontext = 0x7a8
  val scontext = 0x7aa
  val dcsr = 0x7b0
  val dpc = 0x7b1
  val dscratch0 = 0x7b2
  val dscratch1 = 0x7b3
  val mcycle = 0xb00
  val minstret = 0xb02
  val mhpmcounter3 = 0xb03
  val mhpmcounter4 = 0xb04
  val mhpmcounter5 = 0xb05
  val mhpmcounter6 = 0xb06
  val mhpmcounter7 = 0xb07
  val mhpmcounter8 = 0xb08
  val mhpmcounter9 = 0xb09
  val mhpmcounter10 = 0xb0a
  val mhpmcounter11 = 0xb0b
  val mhpmcounter12 = 0xb0c
  val mhpmcounter13 = 0xb0d
  val mhpmcounter14 = 0xb0e
  val mhpmcounter15 = 0xb0f
  val mhpmcounter16 = 0xb10
  val mhpmcounter17 = 0xb11
  val mhpmcounter18 = 0xb12
  val mhpmcounter19 = 0xb13
  val mhpmcounter20 = 0xb14
  val mhpmcounter21 = 0xb15
  val mhpmcounter22 = 0xb16
  val mhpmcounter23 = 0xb17
  val mhpmcounter24 = 0xb18
  val mhpmcounter25 = 0xb19
  val mhpmcounter26 = 0xb1a
  val mhpmcounter27 = 0xb1b
  val mhpmcounter28 = 0xb1c
  val mhpmcounter29 = 0xb1d
  val mhpmcounter30 = 0xb1e
  val mhpmcounter31 = 0xb1f
  val mhpmevent3 = 0x323
  val mhpmevent4 = 0x324
  val mhpmevent5 = 0x325
  val mhpmevent6 = 0x326
  val mhpmevent7 = 0x327
  val mhpmevent8 = 0x328
  val mhpmevent9 = 0x329
  val mhpmevent10 = 0x32a
  val mhpmevent11 = 0x32b
  val mhpmevent12 = 0x32c
  val mhpmevent13 = 0x32d
  val mhpmevent14 = 0x32e
  val mhpmevent15 = 0x32f
  val mhpmevent16 = 0x330
  val mhpmevent17 = 0x331
  val mhpmevent18 = 0x332
  val mhpmevent19 = 0x333
  val mhpmevent20 = 0x334
  val mhpmevent21 = 0x335
  val mhpmevent22 = 0x336
  val mhpmevent23 = 0x337
  val mhpmevent24 = 0x338
  val mhpmevent25 = 0x339
  val mhpmevent26 = 0x33a
  val mhpmevent27 = 0x33b
  val mhpmevent28 = 0x33c
  val mhpmevent29 = 0x33d
  val mhpmevent30 = 0x33e
  val mhpmevent31 = 0x33f
  val mvendorid = 0xf11
  val marchid = 0xf12
  val mimpid = 0xf13
  val mhartid = 0xf14
  val mentropy = 0xf15
  val mnoise = 0x7a9
  val htimedeltah = 0x615
  val cycleh = 0xc80
  val timeh = 0xc81
  val instreth = 0xc82
  val hpmcounter3h = 0xc83
  val hpmcounter4h = 0xc84
  val hpmcounter5h = 0xc85
  val hpmcounter6h = 0xc86
  val hpmcounter7h = 0xc87
  val hpmcounter8h = 0xc88
  val hpmcounter9h = 0xc89
  val hpmcounter10h = 0xc8a
  val hpmcounter11h = 0xc8b
  val hpmcounter12h = 0xc8c
  val hpmcounter13h = 0xc8d
  val hpmcounter14h = 0xc8e
  val hpmcounter15h = 0xc8f
  val hpmcounter16h = 0xc90
  val hpmcounter17h = 0xc91
  val hpmcounter18h = 0xc92
  val hpmcounter19h = 0xc93
  val hpmcounter20h = 0xc94
  val hpmcounter21h = 0xc95
  val hpmcounter22h = 0xc96
  val hpmcounter23h = 0xc97
  val hpmcounter24h = 0xc98
  val hpmcounter25h = 0xc99
  val hpmcounter26h = 0xc9a
  val hpmcounter27h = 0xc9b
  val hpmcounter28h = 0xc9c
  val hpmcounter29h = 0xc9d
  val hpmcounter30h = 0xc9e
  val hpmcounter31h = 0xc9f
  val mstatush = 0x310
  val mcycleh = 0xb80
  val minstreth = 0xb82
  val mhpmcounter3h = 0xb83
  val mhpmcounter4h = 0xb84
  val mhpmcounter5h = 0xb85
  val mhpmcounter6h = 0xb86
  val mhpmcounter7h = 0xb87
  val mhpmcounter8h = 0xb88
  val mhpmcounter9h = 0xb89
  val mhpmcounter10h = 0xb8a
  val mhpmcounter11h = 0xb8b
  val mhpmcounter12h = 0xb8c
  val mhpmcounter13h = 0xb8d
  val mhpmcounter14h = 0xb8e
  val mhpmcounter15h = 0xb8f
  val mhpmcounter16h = 0xb90
  val mhpmcounter17h = 0xb91
  val mhpmcounter18h = 0xb92
  val mhpmcounter19h = 0xb93
  val mhpmcounter20h = 0xb94
  val mhpmcounter21h = 0xb95
  val mhpmcounter22h = 0xb96
  val mhpmcounter23h = 0xb97
  val mhpmcounter24h = 0xb98
  val mhpmcounter25h = 0xb99
  val mhpmcounter26h = 0xb9a
  val mhpmcounter27h = 0xb9b
  val mhpmcounter28h = 0xb9c
  val mhpmcounter29h = 0xb9d
  val mhpmcounter30h = 0xb9e
  val mhpmcounter31h = 0xb9f
  val all = {
    val res = collection.mutable.ArrayBuffer[Int]()
    res += fflags
    res += frm
    res += fcsr
    res += vstart
    res += vxsat
    res += vxrm
    res += vcsr
    res += cycle
    res += time
    res += instret
    res += hpmcounter3
    res += hpmcounter4
    res += hpmcounter5
    res += hpmcounter6
    res += hpmcounter7
    res += hpmcounter8
    res += hpmcounter9
    res += hpmcounter10
    res += hpmcounter11
    res += hpmcounter12
    res += hpmcounter13
    res += hpmcounter14
    res += hpmcounter15
    res += hpmcounter16
    res += hpmcounter17
    res += hpmcounter18
    res += hpmcounter19
    res += hpmcounter20
    res += hpmcounter21
    res += hpmcounter22
    res += hpmcounter23
    res += hpmcounter24
    res += hpmcounter25
    res += hpmcounter26
    res += hpmcounter27
    res += hpmcounter28
    res += hpmcounter29
    res += hpmcounter30
    res += hpmcounter31
    res += vl
    res += vtype
    res += vlenb
    res += sstatus
    res += sedeleg
    res += sideleg
    res += sie
    res += stvec
    res += scounteren
    res += sscratch
    res += sepc
    res += scause
    res += stval
    res += sip
    res += satp
    res += vsstatus
    res += vsie
    res += vstvec
    res += vsscratch
    res += vsepc
    res += vscause
    res += vstval
    res += vsip
    res += vsatp
    res += hstatus
    res += hedeleg
    res += hideleg
    res += hie
    res += htimedelta
    res += hcounteren
    res += hgeie
    res += htval
    res += hip
    res += hvip
    res += htinst
    res += hgatp
    res += hgeip
    res += utvt
    res += unxti
    res += uintstatus
    res += uscratchcsw
    res += uscratchcswl
    res += stvt
    res += snxti
    res += sintstatus
    res += sscratchcsw
    res += sscratchcswl
    res += mtvt
    res += mnxti
    res += mintstatus
    res += mscratchcsw
    res += mscratchcswl
    res += mstatus
    res += misa
    res += medeleg
    res += mideleg
    res += mie
    res += mtvec
    res += mcounteren
    res += mcountinhibit
    res += mscratch
    res += mepc
    res += mcause
    res += mtval
    res += mip
    res += mtinst
    res += mtval2
    res += pmpcfg0
    res += pmpcfg1
    res += pmpcfg2
    res += pmpcfg3
    res += pmpaddr0
    res += pmpaddr1
    res += pmpaddr2
    res += pmpaddr3
    res += pmpaddr4
    res += pmpaddr5
    res += pmpaddr6
    res += pmpaddr7
    res += pmpaddr8
    res += pmpaddr9
    res += pmpaddr10
    res += pmpaddr11
    res += pmpaddr12
    res += pmpaddr13
    res += pmpaddr14
    res += pmpaddr15
    res += tselect
    res += tdata1
    res += tdata2
    res += tdata3
    res += tinfo
    res += tcontrol
    res += mcontext
    res += scontext
    res += dcsr
    res += dpc
    res += dscratch0
    res += dscratch1
    res += mcycle
    res += minstret
    res += mhpmcounter3
    res += mhpmcounter4
    res += mhpmcounter5
    res += mhpmcounter6
    res += mhpmcounter7
    res += mhpmcounter8
    res += mhpmcounter9
    res += mhpmcounter10
    res += mhpmcounter11
    res += mhpmcounter12
    res += mhpmcounter13
    res += mhpmcounter14
    res += mhpmcounter15
    res += mhpmcounter16
    res += mhpmcounter17
    res += mhpmcounter18
    res += mhpmcounter19
    res += mhpmcounter20
    res += mhpmcounter21
    res += mhpmcounter22
    res += mhpmcounter23
    res += mhpmcounter24
    res += mhpmcounter25
    res += mhpmcounter26
    res += mhpmcounter27
    res += mhpmcounter28
    res += mhpmcounter29
    res += mhpmcounter30
    res += mhpmcounter31
    res += mhpmevent3
    res += mhpmevent4
    res += mhpmevent5
    res += mhpmevent6
    res += mhpmevent7
    res += mhpmevent8
    res += mhpmevent9
    res += mhpmevent10
    res += mhpmevent11
    res += mhpmevent12
    res += mhpmevent13
    res += mhpmevent14
    res += mhpmevent15
    res += mhpmevent16
    res += mhpmevent17
    res += mhpmevent18
    res += mhpmevent19
    res += mhpmevent20
    res += mhpmevent21
    res += mhpmevent22
    res += mhpmevent23
    res += mhpmevent24
    res += mhpmevent25
    res += mhpmevent26
    res += mhpmevent27
    res += mhpmevent28
    res += mhpmevent29
    res += mhpmevent30
    res += mhpmevent31
    res += mvendorid
    res += marchid
    res += mimpid
    res += mhartid
    res += mentropy
    res += mnoise
    res.toArray
  }
  val all32 = {
    val res = collection.mutable.ArrayBuffer(all:_*)
    res += htimedeltah
    res += cycleh
    res += timeh
    res += instreth
    res += hpmcounter3h
    res += hpmcounter4h
    res += hpmcounter5h
    res += hpmcounter6h
    res += hpmcounter7h
    res += hpmcounter8h
    res += hpmcounter9h
    res += hpmcounter10h
    res += hpmcounter11h
    res += hpmcounter12h
    res += hpmcounter13h
    res += hpmcounter14h
    res += hpmcounter15h
    res += hpmcounter16h
    res += hpmcounter17h
    res += hpmcounter18h
    res += hpmcounter19h
    res += hpmcounter20h
    res += hpmcounter21h
    res += hpmcounter22h
    res += hpmcounter23h
    res += hpmcounter24h
    res += hpmcounter25h
    res += hpmcounter26h
    res += hpmcounter27h
    res += hpmcounter28h
    res += hpmcounter29h
    res += hpmcounter30h
    res += hpmcounter31h
    res += mstatush
    res += mcycleh
    res += minstreth
    res += mhpmcounter3h
    res += mhpmcounter4h
    res += mhpmcounter5h
    res += mhpmcounter6h
    res += mhpmcounter7h
    res += mhpmcounter8h
    res += mhpmcounter9h
    res += mhpmcounter10h
    res += mhpmcounter11h
    res += mhpmcounter12h
    res += mhpmcounter13h
    res += mhpmcounter14h
    res += mhpmcounter15h
    res += mhpmcounter16h
    res += mhpmcounter17h
    res += mhpmcounter18h
    res += mhpmcounter19h
    res += mhpmcounter20h
    res += mhpmcounter21h
    res += mhpmcounter22h
    res += mhpmcounter23h
    res += mhpmcounter24h
    res += mhpmcounter25h
    res += mhpmcounter26h
    res += mhpmcounter27h
    res += mhpmcounter28h
    res += mhpmcounter29h
    res += mhpmcounter30h
    res += mhpmcounter31h
    res.toArray
  }
}
