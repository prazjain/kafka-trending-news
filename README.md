# Trending News -  End to End Project  

You will have noticed, a lot of news websites contain section on website that tell, the top trending news items of the day.  

__How is the implemented?__  
One option can be, that we just save each news item read count in database, and at the end of day, we run a big query to count the number of time each page was hit.  

__What if the website receive 100K read hits every minute?__  
This will put heavy load on database to save each page's read imprint, slow down response to user's request to get a new article and the database load to run a query to do a group by & count on 1M * 60 * 24 record per day. If you store other critical data on this database, then chances are this query will also  
slow down other operations/services that access this database.  

__What if we need to have most read news items in last 1 minute, 5 minutes, 10 minutes, 6 hours and 24 hours?__  
This will certainly break above solution, as will we need to repetitively run same query for each of above durations, so we have most trending news items stay up to date.  


## Problem Definition  
Lets set scope of our problem.  
* We will design a system that track the news items that have been read on our fictitious news website.  
* We will have scalable architecture that can handle high load of 100K hits per minute.  
* We will display trending news article in last 1 minute, 5 minute, 10 minutes, 6 hours, and 24 hours by maintaining accurate hit rate for 1 min duration, for larger durations we can have slight relaxation on exact hits on news items.    
* As bonus we will also look at top client / browsers used to access our website. This can be useful for our website designers and developers to ensure our news website renders well for these clients.  


