import java.net.URL;
import groovy.text.SimpleTemplateEngine
import groovy.text.GStringTemplateEngine


/**
 * parses the HTML table from a sourceforge.net tracker item list page and results 
 * in a list of maps containing the values for each tracker item.
 * Fields are: 'id','name','status','data','assignee','submitter','prioriy','url'
 * Lastly, it simply prints the id and name of each item.
 */

if(!args || args.length<1){
    println "Usage: URL"
    System.exit(1)
}

def sfurl=args[0]
def url = new URL(sfurl);

def text=url.text

def m = text=~/(?is:.*<table.+?>.*?(<tr>.*<\/tr>).*?<\/table>.*)/

def trs=[]
if(m.matches()){
def tstr=m.group(1)
def m2=tstr=~/(?is:.*?<tr>\s*(.*)\s*<\/tr>.*?)/

    tstr.eachMatch(/(?is:.*?<tr>\s*?(.*?)\s*?<\/tr>.*?)/){trm->
        def matchtr=[]
        
        trm[1].eachMatch(/(?is:.*?<td.+?>\s*?(.*?)\s*?<\/td>)/){tdm->
            matchtr<<tdm[1].trim()
        }
        if(matchtr){
            trs<<matchtr
        }
    }
}


//convert data to context
def vars=['id','name','status','data','assignee','submitter','prioriy']
def data=[]
trs.each{tr->
        def d=[:]
    vars.eachWithIndex{var,x->

        d[var]=tr[x].replaceAll(/<.+?>/,"")
        if('name'==var){
            //extract url
            def murl = tr[x]=~/.*href="(.*?)".*/
            //d['url']=tr[x]
            if(murl.matches()){
                def relurl = murl.group(1)
                def nurl = new URL(url,relurl)
                d['url']=nurl.toExternalForm()
            }
        }

    }
            data<<d
}


//print id and name
data.each{d->
    println "${d.id} ${d.name}"
}
