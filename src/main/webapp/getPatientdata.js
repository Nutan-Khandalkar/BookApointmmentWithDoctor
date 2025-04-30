document.addEventListener("DOMContentLoaded",function(){
	
	getData()
});

function getData() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:8080/Hospital/patientServlet', true);

    xhr.onload = function () {
        if (xhr.status === 200) { // Ensure successful request
            try {
                const data = JSON.parse(xhr.responseText); // Parse JSON response
                console.log("Received Data:", data);
					
				
				let tb=document.getElementById("patientTableBody");
				tb.innerHTML="";
				
                // Log each patient separately
                data.forEach(patient => {
                    console.log(`ID: ${patient.id}, Name: ${patient.name}, Age: ${patient.age}, Gender: ${patient.gender}`);
					
					let row=`<tr>  
					              <td>${patient.id}</td>
					              <td>${patient.name}</td>
					             <td>${patient.age}</td>
					             <td>${patient.gender}</td>
					         </tr>`;	
							 
							 
						tb.innerHTML = tb.innerHTML +row;	 	
                });

            } catch (error) {
                console.error("JSON Parse Error:", error);
            }
        } else {
            console.error("HTTP Error:", xhr.status, xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Network error occurred.");
    };

    xhr.send();
}


