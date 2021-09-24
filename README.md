# chisel-riscv-sc

seccamp 2021 Z-VI

based on https://github.com/gotti/shrv32 and https://github.com/chadyuu/riscv-chisel-book

## test

You can run some test with script, `./test-riscv.sh` or `./test-shadowstack.sh`.

Example binary for shadow-stack testing is located at `./shadowstack-tests/`

This CPU will cause an exception when a shadow-stack error occurred and jump to `mtvec`.

These testing binaries set mtvec to `0x20` and set `gp` to `1` if a shadow-stack error isn't occurred.
