package main

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile
import consts.Consts._

class InstMemIo extends Bundle {
  val Addr = Input(UInt(WORD_LEN.W))
  val Inst = Output(UInt(WORD_LEN.W))
}

class InstMem extends Module {
  val io = IO(new Bundle{
    val instMem = new InstMemIo()
  })
  val instMem = Mem(16384, UInt(8.W))

  loadMemoryFromFile(instMem, "./out")
  io.instMem.Inst := Cat(
    instMem(io.instMem.Addr + 3.U(WORD_LEN.W)), 
    instMem(io.instMem.Addr + 2.U(WORD_LEN.W)),
    instMem(io.instMem.Addr + 1.U(WORD_LEN.W)),
    instMem(io.instMem.Addr)
  )
}

class DataMemIo extends Bundle {
  val ReadAddr = Input(UInt(WORD_LEN.W))
  val ReadData = Output(UInt(WORD_LEN.W))
  val WriteAddr = Input(UInt(WORD_LEN.W))
  val WriteData = Input(UInt(WORD_LEN.W))
  val byte_enable = Input(UInt(DATAMEM_BYTEENABLE_LEN.W))
  val write_enable = Input(Bool())
}

class DataMem extends Module {
  val io = IO(new Bundle{
    val dataMem = new DataMemIo()
  })

  val dataMem = Mem(16384, UInt(8.W))

  io.dataMem.ReadData := Cat(
    Mux(io.dataMem.byte_enable >= DATAMEM_BYTEENABLE_4, dataMem(io.dataMem.ReadAddr + 3.U(WORD_LEN.W)), 0.U),
    Mux(io.dataMem.byte_enable >= DATAMEM_BYTEENABLE_4, dataMem(io.dataMem.ReadAddr + 2.U(WORD_LEN.W)), 0.U),
    Mux(io.dataMem.byte_enable >= DATAMEM_BYTEENABLE_2, dataMem(io.dataMem.ReadAddr + 1.U(WORD_LEN.W)), 0.U),
    Mux(io.dataMem.byte_enable >= DATAMEM_BYTEENABLE_1, dataMem(io.dataMem.ReadAddr), 0.U),
  )
  when (io.dataMem.write_enable===DATAMEM_WE_ENABLE){
    when (io.dataMem.byte_enable >= DATAMEM_BYTEENABLE_1){
      dataMem(io.dataMem.WriteAddr) := io.dataMem.WriteData(7, 0)
    }
    when (io.dataMem.byte_enable >= DATAMEM_BYTEENABLE_2){
      dataMem(io.dataMem.WriteAddr) := io.dataMem.WriteData(15, 8)
    }
    when (io.dataMem.byte_enable >= DATAMEM_BYTEENABLE_4){
      dataMem(io.dataMem.WriteAddr + 2.U(WORD_LEN.W)) := io.dataMem.WriteData(23, 16)
      dataMem(io.dataMem.WriteAddr + 3.U(WORD_LEN.W)) := io.dataMem.WriteData(31, 24)
    }
  }
}
