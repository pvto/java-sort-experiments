cols = c(rgb(0,0,1,1), rgb(1,0,0,1), rgb(0,1,0,1))

t = read.table("bin-100000.csv", header=FALSE, sep=',')
x = t$V22
xlab = "x = n"
ylab = "y = ms"
lbl = "1e5 ~bin(0.5, n)"

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
lbl = "1e5 ~bin(0.25, n)"

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
lbl = "n=1e6 ~ U(0, 1e(x) * 1e6)"

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
lbl = "n=1e6, U(0,1e6) with x * 1e6 random duplications"

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
lbl = "n=1e5, U^4(0,1e5) with x * 1e5 random duplications"

ymax = max(max(t$V2),max(t$V4),max(t$V6))
ymin = 0 #min(min(t$V2),min(t$V4),min(t$V6))
plot(x, t$V4, col=cols[1], ylim=c(ymin,ymax), type="l", xlab="", ylab="")
lines(x, t$V2, col=cols[2])
lines(x, t$V6, col=cols[3])
legend(min(x), 20, c("bleedsort4", "bleedsort3", "Arrays.sort"), col=cols, lty=c(1,1))
title(main=lbl, xlab=xlab, ylab=ylab)



t = read.table("skew-07-5e5.csv", header=FALSE)
x = t$V19
xlab = "x = skew iterations"
ylab = "y = ms"
lbl = "n=5e5, skewed"
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
