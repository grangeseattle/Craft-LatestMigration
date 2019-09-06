$.getJSON("violations.json", function(json) {

	var environment = "";
	var environment2 = "";
	var scenarioTestcase = "";
	var i = 0;
	var totalArrayLength = json.length;

	var issuePages = json;
	console.log("totalArrayLength="+totalArrayLength);
	while (totalArrayLength>0) {
		var eachPage = issuePages[i];
		var pageName;
		var scenarioTestcase;
		var environment;
		for (var j = 0; j < eachPage.length; j++) {

			if (eachPage[j].hasOwnProperty('pagename')) {
				pageName = eachPage[j].pagename;
				scenarioTestcase = eachPage[j].scenariotestcase;
				environment = eachPage[j].environment;

			}
		}

		var tablerow1 = document.createElement('tr');

		var tablecolum1 = document.createElement('td');
		var tablecolum2 = document.createElement('td');

		tablecolum1.innerHTML = "PageName".bold();
		tablecolum2.innerHTML = pageName.bold();

		tablerow1.appendChild(tablecolum1);
		tablerow1.appendChild(tablecolum2);

		document.getElementById("dynamicPerformance").appendChild(tablerow1);

		for (var j = 0; j < eachPage.length; j++) {

			if (eachPage[j].hasOwnProperty('pagename')) {
				continue;
			} else {

				// window.alert(JSON.stringify(eachPage[j]));

				var tablerow2 = document.createElement('tr');

				var tablecolum3 = document.createElement('td');
				var tablecolum4 = document.createElement('td');

				tablecolum3.innerHTML = ("Violations").bold();
				;
				tablecolum4.setAttribute("style", "Font-Weight: bold");
				tablecolum4.innerText = (eachPage[j].Help);

				tablerow2.appendChild(tablecolum3);
				tablerow2.appendChild(tablecolum4);

				var tablerow3 = document.createElement('tr');

				var tablecolum5 = document.createElement('td');
				var tablecolum6 = document.createElement('td');

				tablecolum5.innerHTML = "Description";
				tablecolum6.innerText = eachPage[j].description;

				tablerow3.appendChild(tablecolum5);
				tablerow3.appendChild(tablecolum6);

				var tablerow4 = document.createElement('tr');

				var tablecolum7 = document.createElement('td');
				var tablecolum8 = document.createElement('td');

				tablecolum7.innerHTML = "Recommendations";
				tablecolum8.innerText = eachPage[j].message;

				tablerow4.appendChild(tablecolum7);
				tablerow4.appendChild(tablecolum8);

				var tablerow5 = document.createElement('tr');

				var tablecolum9 = document.createElement('td');
				var tablecolum10 = document.createElement('td');

				tablecolum9.innerHTML = "Issues";
				tablecolum10.innerText = eachPage[j].html;

				tablerow5.appendChild(tablecolum9);
				tablerow5.appendChild(tablecolum10);

				var tablerow6 = document.createElement('tr');

				var tablecolum11 = document.createElement('td');
				var tablecolum12 = document.createElement('td');

				tablecolum11.innerHTML = "Impact";
				tablecolum12.innerHTML = eachPage[j].impact;

				tablerow6.appendChild(tablecolum11);
				tablerow6.appendChild(tablecolum12);

				var tablerow7 = document.createElement('tr');

				var tablecolum13 = document.createElement('td');
				var tablecolum14 = document.createElement('td');

				tablecolum13.innerHTML = "Guidelines";
				tablecolum14.innerHTML = eachPage[j].Guidelines;

				tablerow7.appendChild(tablecolum13);
				tablerow7.appendChild(tablecolum14);

				var tablerow8 = document.createElement('tr');

				var tablecolum15 = document.createElement('td');
				var tablecolum16 = document.createElement('td');

				tablecolum15.innerHTML = "</br>";
				tablecolum16.innerHTML = "</br>";

				tablerow8.appendChild(tablecolum15);
				tablerow8.appendChild(tablecolum16);

				document.getElementById("dynamicPerformance").appendChild(
						tablerow2);
				document.getElementById("dynamicPerformance").appendChild(
						tablerow3);
				document.getElementById("dynamicPerformance").appendChild(
						tablerow4);
				document.getElementById("dynamicPerformance").appendChild(
						tablerow5);
				document.getElementById("dynamicPerformance").appendChild(
						tablerow6);

				document.getElementById("dynamicPerformance").appendChild(
						tablerow7);
				document.getElementById("dynamicPerformance").appendChild(
						tablerow8);

				/* var tablecolum2=document.createElement('td'); */

			}
		}

		var k = i + 1;
		var isFound = false;
		while (k < totalArrayLength) {

			var eachPage2 = issuePages[k];
			var pageName2;
			var scenarioTestcase2;
			var environment2;
			var tempEnv;
			for (var j = 0; j < eachPage2.length; j++) {

				if (eachPage2[j].hasOwnProperty('pagename')) {
					pageName2 = eachPage2[j].pagename;
					scenarioTestcase2 = eachPage2[j].scenariotestcase;
					tempEnv = eachPage2[j].environment;

				}
			}

			if (scenarioTestcase == scenarioTestcase2) {

				if (environment2 == "" && tempEnv != environment) {
					environment2 = tempEnv;
				}

				
				
				var tablerow1 = document.createElement('tr');

				var tablecolum1 = document.createElement('td');
				var tablecolum2 = document.createElement('td');

				tablecolum1.innerHTML = "PageName".bold();
				tablecolum2.innerHTML = pageName2.bold();

				tablerow1.appendChild(tablecolum1);
				tablerow1.appendChild(tablecolum2);

				document.getElementById("dynamicPerformance2").appendChild(tablerow1);

				for (var j = 0; j < eachPage2.length; j++) {

					if (eachPage2[j].hasOwnProperty('pagename')) {
						continue;
					} else {

						// window.alert(JSON.stringify(eachPage[j]));

						var tablerow2 = document.createElement('tr');

						var tablecolum3 = document.createElement('td');
						var tablecolum4 = document.createElement('td');

						tablecolum3.innerHTML = ("Violations").bold();
						;
						tablecolum4.setAttribute("style", "Font-Weight: bold");
						tablecolum4.innerText = (eachPage2[j].Help);

						tablerow2.appendChild(tablecolum3);
						tablerow2.appendChild(tablecolum4);

						var tablerow3 = document.createElement('tr');

						var tablecolum5 = document.createElement('td');
						var tablecolum6 = document.createElement('td');

						tablecolum5.innerHTML = "Description";
						tablecolum6.innerText = eachPage2[j].description;

						tablerow3.appendChild(tablecolum5);
						tablerow3.appendChild(tablecolum6);

						var tablerow4 = document.createElement('tr');

						var tablecolum7 = document.createElement('td');
						var tablecolum8 = document.createElement('td');

						tablecolum7.innerHTML = "Recommendations";
						tablecolum8.innerText = eachPage2[j].message;

						tablerow4.appendChild(tablecolum7);
						tablerow4.appendChild(tablecolum8);

						var tablerow5 = document.createElement('tr');

						var tablecolum9 = document.createElement('td');
						var tablecolum10 = document.createElement('td');

						tablecolum9.innerHTML = "Issues";
						tablecolum10.innerText = eachPage2[j].html;

						tablerow5.appendChild(tablecolum9);
						tablerow5.appendChild(tablecolum10);

						var tablerow6 = document.createElement('tr');

						var tablecolum11 = document.createElement('td');
						var tablecolum12 = document.createElement('td');

						tablecolum11.innerHTML = "Impact";
						tablecolum12.innerHTML = eachPage2[j].impact;

						tablerow6.appendChild(tablecolum11);
						tablerow6.appendChild(tablecolum12);

						var tablerow7 = document.createElement('tr');

						var tablecolum13 = document.createElement('td');
						var tablecolum14 = document.createElement('td');

						tablecolum13.innerHTML = "Guidelines";
						tablecolum14.innerHTML = eachPage2[j].Guidelines;

						tablerow7.appendChild(tablecolum13);
						tablerow7.appendChild(tablecolum14);

						var tablerow8 = document.createElement('tr');

						var tablecolum15 = document.createElement('td');
						var tablecolum16 = document.createElement('td');

						tablecolum15.innerHTML = "</br>";
						tablecolum16.innerHTML = "</br>";

						tablerow8.appendChild(tablecolum15);
						tablerow8.appendChild(tablecolum16);

						document.getElementById("dynamicPerformance2").appendChild(
								tablerow2);
						document.getElementById("dynamicPerformance2").appendChild(
								tablerow3);
						document.getElementById("dynamicPerformance2").appendChild(
								tablerow4);
						document.getElementById("dynamicPerformance2").appendChild(
								tablerow5);
						document.getElementById("dynamicPerformance2").appendChild(
								tablerow6);

						document.getElementById("dynamicPerformance2").appendChild(
								tablerow7);
						document.getElementById("dynamicPerformance2").appendChild(
								tablerow8);
					}
				}
//
				break;
			}
//			
			
		}
		issuePages.splice(i, 1);
		issuePages.splice(k - 1, 1);
		totalArrayLength = totalArrayLength - 2;
//		i++;
	}
	
	document.getElementById("issuesLabel").innerHTML="Results ("+environment+" Vs. "+environment2+")";
	
});

/* @author Immanuel Thomas */