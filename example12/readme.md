# Topic based non-blocking retry

-   This will be one of the most important topic of this series
-   In-case if your scenarios includes the retrying the process of message in case of failure we can use this approach of retry/
```aidl
@RetryableTopic(attempts = "5", backoff = @Backoff(delay = 2_000, maxDelay = 10_000, multiplier = 2))
```
-   attempts => number of times the message should be retried in-case of failure during processing
-   backoff => Specify the backoff properties for retrying this operation
    -   delay => Initial/minimum delay after which the messages should be re-tried ingesting
    -   maxDelay => Maximum delay after which the messages should be re-tried ingesting
    -   multiplier => factor with which the next retry decided
    
-   Look into the code documentation of RetryableTopic which has more option to configure

-   Look into the sample output and relate the behaviour

: ===================================
: Time :: 2021-12-08T22:41:19.389
: value :: fail
: partition :: 0
: offset :: 7
: topic :: topic4
: =================================== 22:41:19
: =================================== 22:41:21 => 2 seconds delay
: Time :: 2021-12-08T22:41:21.910
: value :: fail
: partition :: 0
: offset :: 6
: topic :: topic4-retry-2000
: =================================== 22:41:21.910 = app(22:41:22)
: =================================== 22:41:26                      => 4 seconds delay
: Time :: 2021-12-08T22:41:26.421
: value :: fail
: partition :: 0
: offset :: 4
: topic :: topic4-retry-4000
: =================================== 22:41:26  
: =================================== 22:41:34  => 8 seconds delay  
: Time :: 2021-12-08T22:41:34.934
: value :: fail
: partition :: 0
: offset :: 4
: topic :: topic4-retry-8000
: =================================== 22:41:34.934 = app(22:41:35)
: =================================== 22:41:45 =>  10 seconds delay
: Time :: 2021-12-08T22:41:45.444
: value :: fail
: partition :: 0
: offset :: 4
: topic :: topic4-retry-10000
: ===================================
: =============== DLT Handler ====================
: Time :: 2021-12-08T22:41:45.734
: value :: fail
: partition :: 0
: offset :: 6
: topic :: topic4-dlt
: ===================================
