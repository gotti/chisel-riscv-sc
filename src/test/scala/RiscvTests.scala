package main

import org.scalatest._
import chiseltest._
import chisel3._
import consts.Consts._

class RiscvTest extends FlatSpec with ChiselScalatestTester {
  behavior of "mycpu"
  it should "work through hex" in {
    test(new Top) { c =>
      while (!c.io.pc_is_0x44.peek().litToBoolean){
        c.clock.step(1)
      }
      c.io.gp.expect(1.U)
    }
  }
}
class ShadowStackTest extends FlatSpec with ChiselScalatestTester {
  behavior of "mycpu"
  it should "work through hex" in {
    test(new Top) { c =>
      while (!c.io.exit.peek().litToBoolean){
        c.clock.step(1)
      }
      c.io.shadowstack_is_not_met.expect(false.B)
    }
  }
}
