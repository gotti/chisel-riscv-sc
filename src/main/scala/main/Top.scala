package main

import chisel3._
import chisel3.util._
import consts.Consts._
import chisel3.stage.ChiselStage

class Top extends Module{
  val io = IO(new Bundle{
    val exit = Output(Bool())
    val pc_is_0x44 = Output(Bool())
    val gp = Output(UInt(WORD_LEN.W))
  })

  val cpu = Module(new Cpu())
  val register = Module(new Register())
  val memory = Module(new Memory())
  val shadowStack = Module(new ShadowStack())
  cpu.io.Memory <> memory.io.Memory
  cpu.io.ShadowStack <> shadowStack.io.shadowstack
  cpu.io.regIo <> register.io.regIo
  cpu.io.csrIo <> register.io.csrIo
  cpu.io.exit <> io.exit
  register.io.gp <> io.gp
  cpu.io.pc_is_0x44 <> io.pc_is_0x44

}

object VerilogMain extends App {
  (new ChiselStage).emitVerilog(new Top)
}

