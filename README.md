Coupon system - ejb module
===========================================
this module provides async income logging.
the ejb module received income from JAX-RS and send it to JMS queue
a MessageDrivenBean takes it from the queue and store with JPA to local H2 db

this module shipped with a tester (web module) to simulate coupon system web resources

to build :
1. git clone this coupons-ejb
2. git clone the coupons-ejb-web-tester

add 2 projects to eclipse by File -> Import project from folder

coupons-ejb :
* add WildFly 9.0 runtime to compile.

