# KD9VVBLogger
Simple logging program for amateur radio created using Java 8 and JavaFX.

EDIT: I did a small rework of this program to now export logs as an ADI file, with support for appending to existing files. See https://github.com/heinriche8/KD9VVBLogger_ADI for information

This program was written by myself in an afternoon or so. I personally didn't own any "professional" logging software besides a license to excel that my university provided.
So with that in mind, I decided to actually use what I learned in school and write a simple logging software for amateur radio!

As it stands upon initial release, the program is comprised of several text boxes acting as user input. There are text boxes for the amateur radio band, frequency, and
communication mode. There are also text boxes for the other station's callsign, sent signal report, and received signal report.

I also implemented a couple of text boxes for date and time, as well as a couple of buttons that will automatically fill said text boxes with the user's
system clock-based date and time, with an offset applied automatically to convert to UTC. The user can still enter in any date/time they would wish; UTC formatted or not.

As mentioned in the v1.0.0 release, this program as of Jan. 2023 does not support opening an already existing log. 
<b>If an existing file is selected, it will be completely overwritten.</b>

I understand this is a major issue, however as I had mentioned, this program was written in an afternoon. As such, there aren't many safety checks or input validation
methods implemented. Just the bare minimum to get the program to work.

Feel free to use this program if you desire, but my primary focus is on my education so I will not be publishing very many updates while I am still at university.
