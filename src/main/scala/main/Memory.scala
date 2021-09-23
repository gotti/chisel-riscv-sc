package main

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile
import consts.Consts._

class MemoryIo extends Bundle {
  val InstAddr = Input(UInt(WORD_LEN.W))
  val InstData = Output(UInt(WORD_LEN.W))
  val ReadAddr = Input(UInt(WORD_LEN.W))
  val ReadData = Output(UInt(WORD_LEN.W))
  val WriteAddr = Input(UInt(WORD_LEN.W))
  val WriteData = Input(UInt(WORD_LEN.W))
  val byte_enable = Input(UInt(DATAMEM_BYTEENABLE_LEN.W))
  val write_enable = Input(Bool())
}

class Memory extends Module {
  val io = IO(new Bundle{
    val Memory = new MemoryIo()
  })

  val memory = Mem(16384, UInt(8.W))
  loadMemoryFromFile(memory, "./out")

  io.Memory.InstData := Cat(
    memory(io.Memory.InstAddr + 3.U(WORD_LEN.W)), 
    memory(io.Memory.InstAddr + 2.U(WORD_LEN.W)),
    memory(io.Memory.InstAddr + 1.U(WORD_LEN.W)),
    memory(io.Memory.InstAddr)
  )

  io.Memory.ReadData := Cat(
    Mux(io.Memory.byte_enable >= DATAMEM_BYTEENABLE_4, memory(io.Memory.ReadAddr + 3.U(WORD_LEN.W))(7,0), 0.U(8.W)),
    Mux(io.Memory.byte_enable >= DATAMEM_BYTEENABLE_4, memory(io.Memory.ReadAddr + 2.U(WORD_LEN.W))(7,0), 0.U(8.W)),
    Mux(io.Memory.byte_enable >= DATAMEM_BYTEENABLE_2, memory(io.Memory.ReadAddr + 1.U(WORD_LEN.W))(7,0), 0.U(8.W)),
    Mux(io.Memory.byte_enable >= DATAMEM_BYTEENABLE_1, memory(io.Memory.ReadAddr                  )(7,0), 0.U(8.W)),
  )

  when (io.Memory.write_enable===DATAMEM_WE_ENABLE){
    when (io.Memory.byte_enable >= DATAMEM_BYTEENABLE_1){
      memory(io.Memory.WriteAddr) := io.Memory.WriteData(7, 0)
    }
    when (io.Memory.byte_enable >= DATAMEM_BYTEENABLE_2){
      memory(io.Memory.WriteAddr + 1.U(WORD_LEN.W)) := io.Memory.WriteData(15, 8)
    }
    when (io.Memory.byte_enable >= DATAMEM_BYTEENABLE_4){
      memory(io.Memory.WriteAddr + 2.U(WORD_LEN.W)) := io.Memory.WriteData(23, 16)
      memory(io.Memory.WriteAddr + 3.U(WORD_LEN.W)) := io.Memory.WriteData(31, 24)
    }
  }
}
