 


//function loadPerformance(pagesum,networkcalls){
  $.getJSON("performance.json", function(json) { 
     
      var hits=json;
      var transname, pageloadtime,nwcalls,ttfbyte,totalpage_time,onloadTime,domProcessingTime,recommendations1, priority,des;
      var size=45;


        for(i=0;i<json.Transactions.length;i++){
 //alert("welcome")
         transname=json.Transactions[i].name;
        pageloadtime=json.Transactions[i].restime;
        nwcalls=json.Transactions[i].resourceCount;
        ttfbyte=json.Transactions[i].ttfbUser;
        document.getElementById("ttfbyte").innerHTML=ttfbyte+"ms";
        document.getElementById("ttfb").innerHTML=ttfbyte+"ms";
        totalpage_time=json.Transactions[i].ttfpUser;
        //alert(totalpage_time)
        document.getElementById("pagetime").innerHTML=totalpage_time+"ms";
         document.getElementById("chrome").innerHTML=totalpage_time+"ms";

        onloadTime=json.Transactions[i].onloadTime;
        domProcessingTime=json.Transactions[i].domProcessingTime;
         document.getElementById("dom").innerHTML=domProcessingTime+"ms";
          document.getElementById("onload").innerHTML=onloadTime+"ms";
           document.getElementById("dns").innerHTML="0 ms";
        recommendations1=json.Transactions[i].recommendations;

        for(k=0;k<recommendations1.length;k++){
          priority=recommendations1[k].canvPriority;

          des=recommendations1[k].desc;
          var tablerow1=document.createElement('tr');                                   
          var tablecolum1=document.createElement('td');
          var tablecolum2=document.createElement('td');

         

          if(priority.includes("high")){
            priority="High";
          }
          else if(priority.includes("medium")){
            priority="Medium";
          }
          else if(priority.includes("low")){
            priority="Low";
          }


          tablecolum1.innerHTML=priority.trim();
          tablecolum2.innerHTML=des.trim();


          tablerow1.appendChild(tablecolum1);
          tablerow1.appendChild(tablecolum2);


          document.getElementById("Performance").appendChild(tablerow1); 

        }
      }


       /*$("#chartprofile").empty();  

          loadPerformance1(totalpage_time)*/
 


//}
  

//function loadPerformance1(totalpage_time){
  



  });