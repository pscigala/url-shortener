Simple Url Shortener implementation.

Whole architecture based on distributed base62 id pools approach to scale each node independently to avoid disadvantages of single source of truth.

The pool is generated by url shortener node upon next pool sequence number. The sequence is provided and managed by zookeeper distributed atomic counter.

Redis instance is used as a storage and can be easily scaled out or used as a cache server. 
