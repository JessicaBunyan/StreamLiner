# StreamLiner

## How To

You need .CSV file with round-robin pool information, if you want to try out the software you can use the test input example.csv which can be found in this Repo.  


Set the input and output locations and hit the "Generate Schedules" button and voila - StreamLiner will arrange all the games in order with matches assigned to stations. The matches will be scheduled so the most competitive games will be at different times, allowing them all to be broadcasted in sequence. The games marked for broadcast will be highlighted on the schedules.  


## Libraries 

Requires Apache Commons, ITextPDF, OpenCSV

## Why?

The software is specifically designed to work with the output from the website www.smash.gg, an online tournament hosting platform. smash.gg itself is capable of printing sheets for each round robin group, however these do not have a specific match order / times, nor do they assign matches to specific stations or allow you to schedule matches for live broadcast.

StreamLiner is designed specifically to simplify the brodcasting of the Round Robin phase of a tournament, and allow event organisers to pre-plan which matches are to be broadcast and when