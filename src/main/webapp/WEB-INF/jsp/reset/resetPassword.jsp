<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	 <style>
		    .error_field {
		        color: red; 
		    }
		</style>
	   
	   <title>Inserisci Nuovo Elemento</title>
	 </head>
	   <body class="text-center">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>

			  		<%-- se l'attributo in request ha errori --%>
					<spring:hasBindErrors  name="insert_utente_attr">
						<%-- alert errori --%>
						<div class="alert alert-danger " role="alert">
							Attenzione!! Sono presenti errori di validazione
						</div>
					</spring:hasBindErrors>
			  
			  
			  <div class='conteiner-fluid'>
				    <div class='card-header'>
				        <h5>Cambio Password</h5> 
				    </div>
				    <div class=''>
		
							<form:form modelAttribute="utente_attr" method="post" action="${pageContext.request.contextPath }/reset/executeReset" novalidate="novalidate" class="row g-3">
					
							
								<div class="col-md-12">
									<blockquote><h3>${utente_attr.nome } ${utente_attr.cognome }</h3></blockquote>
								</div>
								
								<div class="col-md-12">
									<blockquote><h4>${utente_attr.username }</h4>
									<p>Desideri cambiare password?</p>
									</blockquote>
								</div>
								 
								<h6 class="card-title p-2 t-2">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
								 
								<div class="row justify-content-center ">
								<div class="col-4">
									<label for="vecchiaPassword" class="form-label">Inserire vecchia password<span class="text-danger">*</span></label>
									<input type="text" placeholder="Inserisci vecchia password" value="${utente_attr.vecchiaPassword }" name="vecchiaPassword" id="vecchiaPassword" class=" form-control ${status.error ? 'is-invalid' : ''}" required>
								</div>
								</div>
								
								<div class="row justify-content-center ">
								<div class="col-4">
									<label for="nuovaPassword" class="form-label">Inserire nuova password<span class="text-danger">*</span></label>
									<input type="text" placeholder="Inserisci nuova password" value="${utente_attr.nuovaPassword }" name="nuovaPassword" id="nuovaPassword" class=" form-control ${status.error ? 'is-invalid' : ''}" required>
								</div>
								</div>
								
								<div class="row justify-content-center ">
								<div class="col-md-4">
									<label for="confermaPassword" class="form-label">Confermare nuova password<span class="text-danger">*</span></label>
									<input type="text" placeholder="Comferma nuova password" value="${utente_attr.confermaPassword }" name="confermaPassword" id="confermaPassword" class=" form-control ${status.error ? 'is-invalid' : ''}" required>
								</div>
								</div>
								
								<div class="col-12">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
									<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
								</div>
		
						</form:form>
  
				    
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		
					  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>