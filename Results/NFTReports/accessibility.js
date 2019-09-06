     $.getJSON("access.json", function(json) {
            var hitss=json.hits.hits;
            var currentbuildNumber=0;
            var previousbuildNumber=0;
			
			//window.alert(hitss);
            for(var i=0;i<hitss.length;i++)
            {
   
			 //window.alert(parseInt(hitss[i]._source["runid"]));
			 //window.alert("hitslength"+hitss.length);
                if(i==0)
                {
					//currentbuildNumber=parseInt(hits[i]._source("rundetails");
					//window.alert("insideif"+currentbuildNumber);
                 currentbuildNumber=parseInt(hitss[i]._source["runid"]);
				 //window.alert("currentbuildNumber"+currentbuildNumber);
                }
                else if(i==1)
                {

                previousbuildNumber=parseInt(hitss[i]._source["runid"]);
                if(currentbuildNumber<previousbuildNumber)
                {
                    var temp=currentbuildNumber;
                    currentbuildNumber=previousbuildNumber;
                    previousbuildNumber=temp;
					//window.alert(currentbuildNumber<previousbuildNumber);
                }
                }
                 else
                   {
                    if(currentbuildNumber<parseInt(hitss[i]._source["runid"]))
                    {
                        previousbuildNumber=currentbuildNumber;
                        currentbuildNumber=parseInt(hitss[i]._source["runid"]);
                    }
                    else if (previousbuildNumber<parseInt(hitss[i]._source["runid"])) {
                        previousbuildNumber= parseInt(hitss[i]._source["runid"]);
                    }
            }
 
               
            }
          //window.alert(previousbuildNumber+":"+currentbuildNumber);
            var currentObject,previousObject;
			 // window.alert("scope2"+hitss.length);
            for(var i=0;i<hitss.length;i++)
            {
                if(parseInt(hitss[i]._source["runid"])==currentbuildNumber)
                {
                        currentObject=hitss[i]._source["rundetails"];
                }
                else if(parseInt(hitss[i]._source["runid"])==previousbuildNumber)
                {
                    previousObject=hitss[i]._source["rundetails"];
                }
            }

          //window.alert(JSON.stringify(currentObject));
           // window.alert(JSON.stringify(previousObject));
              for(var i=0;i<currentObject.length;i++)
            {


                for(var j=0;j<previousObject.length;j++)
                {

                    if(currentObject[i].pagename==previousObject[j].pagename)
                    {
                         var tablerow=document.createElement('tr');
                
                    var tablecolum1=document.createElement('td');
                    var tablecolum2=document.createElement('td');
                    var tablecolum3=document.createElement('td');
                    var tablecolum4=document.createElement('td');
                    var tablecolum5=document.createElement('td');
                    var tablecolum6=document.createElement('td');
                    var tablecolum7=document.createElement('td');
                    var tablecolum8=document.createElement('td');
                    var tablecolum9=document.createElement('td');


                    tablecolum1.innerHTML=currentObject[i].pagename;
                     tablecolum2.innerHTML=currentObject[i].critical;
                     tablecolum3.innerHTML=previousObject[j].critical;

                    tablecolum4.innerHTML=currentObject[i].serious;
                    tablecolum5.innerHTML=previousObject[j].serious;
                    tablecolum6.innerHTML=currentObject[i].moderate;
                    tablecolum7.innerHTML=previousObject[j].moderate;

                    tablecolum8.innerHTML=currentObject[i].minor;
                    tablecolum9.innerHTML=previousObject[j].minor;


                    tablerow.appendChild(tablecolum1);
                    tablerow.appendChild(tablecolum2);
                    tablerow.appendChild(tablecolum3);
                    tablerow.appendChild(tablecolum4);
                    tablerow.appendChild(tablecolum5);
                    tablerow.appendChild(tablecolum6);
                    tablerow.appendChild(tablecolum7);
                    tablerow.appendChild(tablecolum8);
                    tablerow.appendChild(tablecolum9);

                

                

                    
               document.getElementById("accessibilitycompare").appendChild(tablerow);  

                    }

                }

            }

            document.getElementById("runid").innerHTML=currentbuildNumber;
             document.getElementById("pages").innerHTML=currentObject.length;




            });