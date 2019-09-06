 $.ajaxSetup({
    async: false
});  


function recommendations(name)
 {
 	  alert("welcome")
  $.getJSON("performance.json", function(json) { 
    
      var hits=json;
      var transname,recommendations1, priority,des;
      var size=45;


      							var div=document.createElement('div');
      							div.setAttribute("data-role", "collapsible-set data-content-theme=d id=set");
      							 var table = document.createElement('table');
                                table.setAttribute("class","table table-bordered table-hover table-striped");
                                table.setAttribute("id","table");

                                var tableheader=document.createElement('thead');
                                var tableheadrow=document.createElement('tr');
                                var tableheader0=document.createElement('th');
                                var tableheader1=document.createElement('th');
                                var tablebody=document.createElement('tbody');
                                tableheader0.innerHTML="Priority";
                                tableheader1.innerHTML="Description";
                                tableheadrow.appendChild(tableheader0);
                                tableheadrow.appendChild(tableheader1);
                                tableheader.appendChild(tableheadrow);


        for(i=0;i<json.Transactions.length;i++)
        {
         transname=json.Transactions[i].name;
         if(transname==name)
         {
       
        recommendations1=json.Transactions[i].recommendations;   
        /*if (name==title){  */                        // oldnode.replaceChild(tablerow,oldnode.childNodes[0]);

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
          tablebody.appendChild(tablerow1);
          table.appendChild(tableheader);
          table.appendChild(tablebody)

          document.getElementById("tablerow").appendChild(table); 

     //   }
		    }
		}
	}
      });
    }