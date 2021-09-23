#include <iostream>
#include <verilated.h>
#include <verilated_vcd_c.h>
#include "VTop.h"

int time_counter = 0;
int main(int argc, char **argv) {
    Verilated::commandArgs(argc,argv);
    VTop *cpu = new VTop();
    Verilated::traceEverOn(true);
    VerilatedVcdC* tfp = new VerilatedVcdC;
    cpu->trace(tfp, 100);  // Trace 100 levels of hierarchy
    tfp->open("simx.vcd");
    cpu->clock = 0;
    cpu->reset = 0;
    while (time_counter < 500){
        cpu->eval();
        tfp->dump(time_counter);  // 波形ダンプ用の記述を追加
        time_counter++;
    }
    while (time_counter < 1000){
        cpu->eval();
        tfp->dump(time_counter);  // 波形ダンプ用の記述を追加
        time_counter++;
        cpu->reset = 1;
    }
    cpu->reset = 0;
    while (time_counter < 1000000) {
        if ((time_counter % 5) == 0) {
          cpu->clock = !cpu->clock; // Toggle clock
        }
        cpu->eval();
        tfp->dump(time_counter);  // 波形ダンプ用の記述を追加
        time_counter++;
    }
    
    cpu->final();
    tfp->close();
}
