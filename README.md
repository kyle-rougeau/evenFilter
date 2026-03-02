This is a simple proof of concept, so the ux is pretty bad.

Currently set up to process and time a large amount of random inputs

In order to print output (this will slow down execution dramatically, so you should reduce sample size by at least 1000x) uncomment the println lines in each method use ctrl+f as needed.

For testing purposes, a "test" sample is provided and may be changed indiscriminantly. BE AWARE the test sample only has 31 elements, reduce sample size accordingly.

All of the main calculations are made using integer logic; however, rational outputs are kind of supported
  In order to calculate rational outputs you have to change the numeric precision variable in terms of units after decimal point
  Due to rounding errors and other things, precision is not exact, so add 2 or 3 as necessary.
  the round to variable determines the appearance of the number in output (this one is exact).

I plan on eventually supporting integer inputs, but it is currently technically possible for the ab filter since the entire function is normalized by a1
