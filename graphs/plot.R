cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("bin-100000.csv", header=FALSE, sep=' ')
x = t$V21
xlab = "x = n"
ylab = "y = ms"
lbl = "1e5, ~bin(0.5, n)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)





cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("bin-025-1e5.csv", header=FALSE, sep=' ')
x = t$V21
xlab = "x = n"
ylab = "y = ms"
lbl = "1e5, ~bin(0.25, n)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)





t = read.table("bin-01-n.csv", header=FALSE, sep=' ')
x = t$V9
xlab = "x = array size"
ylab = "y = ms"
lbl = "~bin(0.1, 10000)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, log="xy", col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)
text(x, t$V4, round(t$V4, 2), cex=1)
text(x, t$V6, round(t$V6, 2), cex=1)
grid(12,12)



cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("decr-1e4.csv", header=FALSE)
x = t$V11
xlab = "x = item random duplication factor"
ylab = "y = ms"
lbl = "1e4, monotonously decreasing at x=0"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0  #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)





cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("decr-1e5.csv", header=FALSE)
x = t$V11
xlab = "x = item random duplication factor"
ylab = "y = ms"
lbl = "1e5, monotonously decreasing at x=0"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)




cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("decr-1e6.csv", header=FALSE)
x = t$V11
xlab = "x = item random duplication factor"
ylab = "y = ms"
lbl = "1e6, monotonously decreasing at x=0"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0  #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)




cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("rnd-1e5.csv", header=FALSE)
x = log(t$V13, 10)
xlab = "x = linear scatter"
ylab = "y = ms"
lbl = "1e5, U(0, 1e(x) * 1e5)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0  #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)




cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("rnd-1e6.csv", header=FALSE)
x = log(t$V13, 10)
xlab = "x = linear scatter"
ylab = "y = ms"
lbl = "1e6, ~U(0, 1e(x) * 1e6)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0  #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)




cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("rnd-simil-1e6.csv", header=FALSE)
x = t$V11
xlab = "x = item random duplication factor"
ylab = "y = ms"
lbl = "1e6, U(0,1e6) with x * 1e6 random duplications"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), 20, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)




t = read.table("rnd-simil-exp-4-1e5.csv", header=FALSE)
x = t$V11
xlab = "x = item random duplication factor"
ylab = "y = ms"
lbl = "1e5, U^4(0,1e5) with x * 1e5 random duplications"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), 20, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)



t = read.table("rnd-exp-dupl020-3e4.csv", header=FALSE)
x = t$V19
xlab = "x = exponent of U"
ylab = "y = ms"
lbl = "3e4, ~ U^x(0,1) * 3e4, with 2e4 random duplications"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), 20, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)



cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("rnd-u.csv", header=FALSE)
x = t$V9
xlab = "x = array size"
ylab = "y = ms"
lbl = "~U(0, x)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0  #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
points(x, t$V4, col=cols[1])
lines(x, t$V2, col=cols[2]);  points(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3]);  points(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)
grid (NULL,NULL, lty = 6, col = "cornsilk2")



t = read.table("skew-07-5e5.csv", header=FALSE)
x = t$V19
xlab = "x = skew iterations"
ylab = "y = ms"
lbl = "5e5, skewed"
sub = "for t in [1..x]: pick z from U(0,1) iff z < 0.7^t; return first such t * 5e5"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), 20, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, sub=sub, xlab=xlab, ylab=ylab)
#text(x, t$V4, round(t$V4, 2), cex=.75) text(x, t$V6, round(t$V6, 2), cex=.75)
grid (NULL,NULL, lty = 6, col = "cornsilk2")




cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("bin2-p02-5e4.csv", header=FALSE, sep=' ')
x = t$V21
xlab = "x = n"
ylab = "y = ms"
lbl = "5e4, ~ bin(0.2, n) + bin(0.8, n)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)
grid (NULL,NULL, lty = 6, col = "cornsilk2")



cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("bin2-p01-5e3.csv", header=FALSE, sep=' ')
x = t$V21
xlab = "x = n"
ylab = "y = ms"
lbl = "5000, ~ (bin(0.1, n) + bin(0.9, n))"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), 0.027, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)
grid (NULL,NULL, lty = 6, col = "cornsilk2")




t = read.table("bin2-5e6.csv", header=FALSE, sep=' ')
x = t$V17
xlab = "x = p"
ylab = "y = ms"
lbl = "5e6,  ~ (bin(p, 5000) + bin(1-p, 5000))"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), 27, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)
grid (NULL,NULL, lty = 6, col = "cornsilk2")


t = read.table("bin2-p025-n10.csv", header=FALSE, sep=' ')
x = t$V9
xlab = "x = array size"
ylab = "y = ms"
lbl = "~ (bin(0.25, 10) + bin(0.75, 10))"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)
grid (NULL,NULL, lty = 6, col = "cornsilk2")



############## bleedsort4b
###########################

cols = c(rgb(0,0,1,1), rgb(0,.5,1,1), rgb(0,1,0,1))

t = read.table("bleedsort-4b-times.txt", header=FALSE)
t = subset(t, t$V21 == 1006)
t = t[order(t$V11),]
x = t$V11

xlab = "x = item random duplication factor"
ylab = "y = ms"
lbl = "1e6, U(0,1e6) with x * 1e6 random duplications"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), 20, c("bleedsort4b", "bleedsort4", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)



t = read.table("bleedsort-4b-times.txt", header=FALSE)
t = subset(t, t$V19 == -1.004)
t = t[order(t$V9),]
x = t$V9
xlab = "x = array size"
ylab = "y = ms"
lbl = "~bin(0.1, 10000)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, log="xy", col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
#points(x, t$V4, col=cols[1])
lines(x, t$V2, col=cols[2])
#points(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
#points(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4b", "bleedsort4", "Arrays.sort"), col=cols, lty=c(1,1))
text(x, t$V4, round(t$V4, 2), cex=1)
text(x, t$V6, round(t$V6, 2), cex=1)
title(main=lbl, xlab=xlab, ylab=ylab)
grid (NULL,NULL, lty = 6, col = "cornsilk2")




t = read.table("bleedsort-4b-times.txt", header=FALSE)
t = subset(t, t$V21 == 1007)
t = t[order(t$V19),]
x = t$V19
xlab = "x = exponent of U"
ylab = "y = ms"
lbl = "3e4, ~ U^x(0,1) * 3e4, with 3e4 random duplications"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(max(x) * 0.6, ymax, c("bleedsort4b", "bleedsort4", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)



t = read.table("bleedsort-4b-times.txt", header=FALSE)
t = subset(t, t$V21 == 1010)
t = t[order(t$V19),]
x = t$V19
xlab = "x = skew iterations"
ylab = "y = ms"
lbl = "5e5, skewed"
sub = "for t in [1..x]: pick z from U(0,1) iff z < 0.7^t; return first such t * 5e5"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), 20, c("bleedsort4b", "bleedsort4", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, sub=sub, xlab=xlab, ylab=ylab)
#text(x, t$V4, round(t$V4, 2), cex=.75) text(x, t$V6, round(t$V6, 2), cex=.75)
grid (NULL,NULL, lty = 6, col = "cornsilk2")


#bs4-decr-mix01-comp001-1e6
t = read.table("bleedsort-4b-times.txt", header=FALSE)
t = subset(t, t$V21 == 1012)
t = t[order(t$V11),]
x = t$V11
xlab = "x = item random duplication factor"
ylab = "y = ms"
lbl = "1e6, monotonously decreasing at x=0"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0  #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend((max(x)-min(x))*0.72, 10, c("bleedsort4b", "bleedsort4", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)


t = read.table("bleedsort-4b-times.txt", header=FALSE)
t = subset(t, t$V21 == 1013)
t = t[order(t$V15),]
x = t$V15
xlab = "x = mixing factor"
ylab = "y = ms"
lbl = "1e6, monotonously decreasing, range [0,1e5]"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0  #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c("bleedsort4b", "bleedsort4", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)




cols = c(rgb(0,0,1,1), rgb(0,.5,1,1), rgb(0,1,0,1))


t = read.table("bleedsort-4b2-times.txt", header=FALSE)
t = subset(t, t$V21 == 1014)
t = t[order(t$V27),]
x = t$V27
xlab = "x = frequency"
ylab = "y = ms"
lbl = "1e5, sin(i*2*PI*x) * 1e5"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(10000, 3.1, c("bleedsort4b", "bleedsort4", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)



t = read.table("bleedsort-4b2-times.txt", header=FALSE)
t = subset(t, t$V21 == 1015)
t = t[order(t$V31),]
x = t$V31
xlab = "x = mix frequency"
ylab = "y = ms"
lbl = "1e5, sin(i*2*PI*x) * 1e5, 1/10 items mixed with freq x"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymin+(ymax-ymin)*0.2, c("bleedsort4b", "bleedsort4", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)




t = read.table("bleedsort-4b2-times.txt", header=FALSE)
t = subset(t, t$V21 == 1016)
t = t[order(t$V15),]
x = t$V15
xlab = "x = swap operations, multiple of array size"
ylab = "y = ms"
lbl = "1e5, sinusoidal curve, nearby items [x,y] swapped"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymin+(ymax-ymin)*0.2, c("bleedsort4b", "bleedsort4", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)





##################################Bleedsort5
cols = c(rgb(0.4,0.6,0,1), rgb(0,0,1,1), rgb(0,1,0,1), rgb(0,.5,1,1))


t = read.table("bleedsort-5-times.txt", header=FALSE)
t = subset(t, t$V19 == 1008)
t = t[order(t$V21),]
x = t$V21
xlab = "x = n"
ylab = "y = ms"
lbl = "1e5, ~bin(0.5, n)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymin+(ymax-ymin)*0.2, c("bleedsort5", "bleedsort4b", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)
grid (NULL,NULL, lty = 6, col = "cornsilk2")





cols = c(rgb(0.4,0.6,0,1), rgb(0,1,0,1), rgb(0,0,1,1), rgb(0,.5,1,1))
t = read.table("bleedsort-5-times.txt", header=FALSE)
t = subset(t, t$V19 == 1008 & t$V21 == 10000)
t = t[order(t$V9),]
x = t$V9
xlab = "x = array size"
ylab = "y = ms"
lbl = "1e5, ~bin(0.1, 10000)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, log="xy", col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
#lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymin+(ymax-ymin)*0.2, c("bleedsort5", #"bleedsort4b",
    "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)
grid (NULL,NULL, lty = 6, col = "cornsilk2")
text(x, t$V4, round(t$V4, 2), cex=.75)
text(x, t$V6, paste( '*', round(t$V6/t$V4, 1) ), cex=.75)



cols = c(rgb(0.4,0.6,0,1), rgb(0,0,1,1), rgb(0,1,0,1), rgb(0,.5,1,1))
t = read.table("bleedsort-5-times.txt", header=FALSE)
t = subset(t, t$V21 == 1019)
t = t[order(t$V19),]
x = t$V19
xlab = "x = exponent of U"
ylab = "y = ms"
lbl = "n=3e4, ~ U^x(0,1) * 3e4, with 3e4 random duplications"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
grid (NULL,NULL, lty = 6, col = "cornsilk2")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x)+(max(x)-min(x))*0.72, ymax, c("bleedsort5", "bleedsort4b", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)




cols = c(rgb(0.3,0.4,0,1), rgb(0,0,1,1), rgb(0,1,0,1), rgb(0,.5,1,1))
t = read.table("bleedsort-5-times.txt", header=FALSE)
t = subset(t, t$V21 == 1021)
t = t[order(t$V29),]
x = t$V29
xlab = "x = sine altitude"
ylab = "y = ms"
lbl = "1e5, sinusoidal wave, different parameters"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, pch=3, col=cols[1], ylim=c(ymin,ymax), type="p", xlab="", ylab="")
grid (NULL,NULL, lty = 6, col = "cornsilk2")
points(x, t$V2, col=cols[2])
points(x, t$V6, col=cols[3])
legend(min(x)+(max(x)-min(x))*0.62, ymax, c(
    paste( "bleedsort5 ", round(sum(t$V4)/length(t$V4), 2) ),
    paste( "bleedsort4b", round(sum(t$V2)/length(t$V2), 2) ),
    paste( "Arrays.sort", round(sum(t$V6)/length(t$V6), 2) )
  ), , col=cols, lty=c(1,1)
)
title(main=lbl, xlab=xlab, ylab=ylab)





cols = c(rgb(0.3,0.4,0,1), rgb(0,0,1,1), rgb(0,1,0,1), rgb(0,.5,1,1))
t = read.table("bleedsort-5-times.txt", header=FALSE)
t = subset(t, t$V21 == 1022)
t = t[order(t$V29),]
x = t$V29
xlab = "x = sine altitude"
ylab = "y = ms"
lbl = "1e7, sinusoidal wave, freq. 18, mix 200%"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, log="xy", pch=3, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
grid (NULL,NULL, lty = 6, col = "cornsilk2")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), ymax, c(
    paste( "bleedsort5 μ=", round(sum(t$V4)/length(t$V4), 2) ),
    paste( "bleedsort4b μ=", round(sum(t$V2)/length(t$V2), 2) ),
    paste( "Arrays.sort μ=", round(sum(t$V6)/length(t$V6), 2) )
  ), , col=cols, lty=c(1,1)
)
title(main=lbl, xlab=xlab, ylab=ylab)





######################################## Bleedsort5 - round 2

cols = c(rgb(0.3,0.4,0,1), rgb(0,0,1,1), rgb(0,1,0,1), rgb(0,.5,1,1))
t = read.table("bleedsort-5-times.txt", header=FALSE)
t = subset(t, t$V19 == 1023 & t$V21 == 10000)
t = t[order(t$V9),]
x = t$V9
xlab = "x = array size"
ylab = "y = ms"
lbl = "1e5, ~bin(0.1, 10000)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, log="xy", pch=3, col=cols[1], ylim=c(ymin,ymax), type="p", xlab="", ylab="")
points(x, t$V2, pch=4, col=cols[2])
points(x, t$V6, pch=1, col=cols[3])
legend(min(x), ymin+(ymax-ymin)*0.2, c(
    paste( "bleedsort5 μ=", round(sum(t$V4)/length(t$V4), 2) ),
    paste( "bleedsort4b μ=", round(sum(t$V2)/length(t$V2), 2) ),
    paste( "Arrays.sort μ=", round(sum(t$V6)/length(t$V6), 2) )
#    "bleedsort5", "bleedsort4b", "Arrays.sort"
), col=cols, #lty=c(1,1),
    pch=c(3,4,1))
title(main=lbl, xlab=xlab, ylab=ylab)
grid (NULL,NULL, lty = 6, col = "cornsilk2")
#text(x, t$V4, round(t$V4, 2), cex=.75)
#text(x, t$V6, paste( '*', round(t$V6/t$V4, 1) ), cex=.75)





cols = c(rgb(0.3,0.4,0,1), rgb(0,0,1,1), rgb(0,1,0,1), rgb(0,.5,1,1))
t = read.table("bleedsort-5-times.txt", header=FALSE)
t = subset(t, t$V19 == 1024)
t = t[order(t$V9),]
x = t$V9
xlab = "x = array size"
ylab = "y = ms"
lbl = "1e5, ~ bin(0.25, 10) + bin(0.75, 10)"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, log="xy", pch=3, col=cols[1], ylim=c(ymin,ymax), type="p", xlab="", ylab="")
points(x, t$V2, pch=4, col=cols[2])
points(x, t$V6, pch=1, col=cols[3])
legend(min(x), ymin+(ymax-ymin)*0.2, c(
    paste( "bleedsort5 μ=", round(sum(t$V4)/length(t$V4), 2) ),
    paste( "bleedsort4b μ=", round(sum(t$V2)/length(t$V2), 2) ),
    paste( "Arrays.sort μ=", round(sum(t$V6)/length(t$V6), 2) )
#    "bleedsort5", "bleedsort4b", "Arrays.sort"
), col=cols, #lty=c(1,1),
    pch=c(3,4,1))
title(main=lbl, xlab=xlab, ylab=ylab)
grid (NULL,NULL, lty = 6, col = "cornsilk2")
#text(x, t$V4, round(t$V4, 2), cex=.75)
#text(x, t$V6, paste( '*', round(t$V6/t$V4, 1) ), cex=.75)
