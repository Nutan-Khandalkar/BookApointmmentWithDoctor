
document.addEventListener("DOMContentLoaded",function(){
   getData();
});

function getData(){
	
	const xhr =new XMLHttpRequest();
	xhr.open('GET','http://localhost:8080/Hospital/doctorServlet',true);
	
	xhr.onload=function(){
		if(this.status==200){
			try{
			const data = JSON.parse(xhr.responseText);
			console.log("Received Data:", data);
			
			
			let  tb=document.getElementById("patientTableBody");
			tb.innerHTML="";
			
			data.forEach(Dr=>{
				
				let row= `<tr>
				           <td>${Dr.id}</td>
						   <td>${Dr.name}</td>
						   <td>${Dr.specielisation}</td>      
				         </tr>`
						 
						 tb.innerHTML=tb.innerHTML+row;
				
			});
			}catch(error){
				console.log("Error in fetching data",error);
			}
		}else{
			console.log("HTTP Error:", xhr.status, xhr.statusText);
		}
	}
	
	xhr.send();
}
