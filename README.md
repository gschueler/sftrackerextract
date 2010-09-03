Extract Sourceforge.net tracker data.
----------------------

*author*: Greg Schueler, <greg.schueler@gmail.com>
*date*: 9/2/2010

This is a simple groovy script that grabs the table data from a tracker page on sourceforge.net, and then prints
the id and name of each item found.

The last println could be changed to print more of the extracted data.

fields extracted for each tracker item in the table:

   'id','name','status','data','assignee','submitter','prioriy','url'
