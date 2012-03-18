#Gnuplot script
set yrange [0:150]
set title "Frustration over Time"
set ylabel "Frustration Factor"
set xlabel "Time (in cycles)"

plot "output.txt" using 1:2 with lines title 'Unrecognized Input', "output.txt" using 1:3 with lines title 'Repeated Commands', "output.txt" using 1:4 smooth bezier with lines title 'Moves Since Last Plotpoint', "output.txt" using 1:5 with lines title 'Total Frustration', "output.txt" using 1:6 with lines title 'Quest Active', "output.txt" using 1:7 with lines title 'Frustration Limit', "output.txt" using 1:8 with lines title 'Very Frustrated High Water Mark'