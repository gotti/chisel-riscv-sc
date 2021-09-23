package main

import chisel3._
import consts.Consts._

class ShadowStackIo extends Bundle {
  val push_enable = Input(Bool())
  val pop_enable = Input(Bool())
  val readData = Output(UInt(WORD_LEN.W))
  val writeData  = Input(UInt(WORD_LEN.W))
}
class ShadowStack extends Module {
  val io = IO(new Bundle {
    val shadowstack = new ShadowStackIo()
  })

  val stack = Mem(1024, UInt(32.W))
  val stack_pointer = RegInit(0.U(10.W))

  io.shadowstack.readData := stack(stack_pointer-1.U)
  when(io.shadowstack.push_enable) { //上限こえたときどうしよ
    stack(stack_pointer) := io.shadowstack.writeData
    stack_pointer := stack_pointer + 1.U
  } .elsewhen(io.shadowstack.pop_enable) {
    stack_pointer := stack_pointer - 1.U
  }
}
