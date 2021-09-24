package main

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile
import consts.Consts._
import consts._

class RegisterIo extends Bundle {
  val rs1_address: UInt = Input(UInt(WORD_ADDRESS_LEN.W))
  val rs2_address: UInt = Input(UInt(WORD_ADDRESS_LEN.W))
  val rd_address: UInt = Input(UInt(WORD_ADDRESS_LEN.W))
  val rd_writeback: UInt = Input(UInt(WORD_LEN.W))
  val rd_writeControl: UInt = Input(Bool())
  val rs1_value: UInt = Output(UInt(WORD_LEN.W))
  val rs2_value: UInt = Output(UInt(WORD_LEN.W))
}

class CsrRegisterIo extends Bundle {
  val csr_read_address: UInt = Input(UInt(CSR_ADDRESS_LEN.W))
  val csr_write_address: UInt = Input(UInt(CSR_ADDRESS_LEN.W))
  val csr_read_value: UInt = Output(UInt(CSR_DATA_LEN.W))
  val csr_write_value: UInt = Input(UInt(CSR_DATA_LEN.W))
  val csr_write_enable: UInt = Input(Bool())
}

class Register extends Module{
  val io = IO(new Bundle{
    val regIo = new RegisterIo()
    val csrIo = new CsrRegisterIo()
    val gp: UInt = Output(UInt(WORD_LEN.W))
  })
  val registers: Mem[UInt] = Mem(32, UInt(32.W))

  def read_register( address:UInt ): UInt = {
    val ret: UInt = Wire(UInt(WORD_LEN.W))
    when (address===0.U) {
      ret := 0.U(WORD_LEN.U)
    } .otherwise {
      ret := registers(address)
    }
    ret
  }

  io.gp := read_register(3.U(WORD_LEN.W))
  io.regIo.rs1_value := read_register(io.regIo.rs1_address)
  io.regIo.rs2_value := read_register(io.regIo.rs2_address)

  when(io.regIo.rd_writeControl===RWB_ENABLE) {
    registers(io.regIo.rd_address) := io.regIo.rd_writeback
  }

  val csr_registers: Mem[UInt] = Mem(4096, UInt(32.W))
  io.csrIo.csr_read_value := csr_registers(io.csrIo.csr_read_address)
  when(io.csrIo.csr_write_enable===RWB_ENABLE) {
    csr_registers(io.csrIo.csr_write_address) := io.csrIo.csr_write_value
  }

  printf(p"${Hexadecimal(registers(0))}\t${Hexadecimal(registers(1))}\t${Hexadecimal(registers(2))}\t${Hexadecimal(registers(3))}\t${Hexadecimal(registers(4))}\t${Hexadecimal(registers(5))}\t${Hexadecimal(registers(6))}\t${Hexadecimal(registers(7))}\n")
  printf(p"${Hexadecimal(registers(8))}\t${Hexadecimal(registers(9))}\t${Hexadecimal(registers(10))}\t${Hexadecimal(registers(11))}\t${Hexadecimal(registers(12))}\t${Hexadecimal(registers(13))}\t${Hexadecimal(registers(14))}\t${Hexadecimal(registers(15))}\n")
  printf(p"${Hexadecimal(registers(16))}\t${Hexadecimal(registers(17))}\t${Hexadecimal(registers(18))}\t${Hexadecimal(registers(19))}\t${Hexadecimal(registers(20))}\t${Hexadecimal(registers(21))}\t${Hexadecimal(registers(22))}\t${Hexadecimal(registers(23))}\n")
  printf(p"${Hexadecimal(registers(24))}\t${Hexadecimal(registers(25))}\t${Hexadecimal(registers(26))}\t${Hexadecimal(registers(27))}\t${Hexadecimal(registers(28))}\t${Hexadecimal(registers(29))}\t${Hexadecimal(registers(30))}\t${Hexadecimal(registers(31))}\n")
  printf(p"CSR_MTVEC=${Hexadecimal(csr_registers(CSRs.mtvec))}\n")
  printf(p"CSR_WE=${io.csrIo.csr_write_enable}\n")
  printf(p"CSR_wa=${io.csrIo.csr_write_address}\n")
  printf(p"CSR_wv=${io.csrIo.csr_write_value}\n")
  printf(p"CSR_ra=${io.csrIo.csr_read_address}\n")
  printf(p"CSR_rv=${io.csrIo.csr_read_value}\n")
}
