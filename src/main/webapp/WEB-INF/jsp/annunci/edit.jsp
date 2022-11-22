<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100">
	<head>
		<jsp:include page="../header.jsp" />
		
	    <style>
		    .error_field {
		        color: red; 
		    }
		</style>
		<title>Modifica Elemento</title>
	</head>
	<body class="d-flex flex-column h-100">
		<jsp:include page="../navbar.jsp" />
		
			<!-- Begin page content -->
			<main class="flex-shrink-0">
				<div class="container">
		
					<%-- se l'attributo in request ha errori --%>
					<spring:hasBindErrors  name="edit_regista_attr">
						<%-- alert errori --%>
						<div class="alert alert-danger " role="alert">
							Attenzione!! Sono presenti errori di validazione
						</div>
					</spring:hasBindErrors>
				
					<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					
					<div class='card'>
					    <div class='card-header'>
					        <h5>Modifica elemento</h5> 
					    </div>
					    <div class='card-body'>
				    
				    		<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		
							<form:form modelAttribute="edit_annunci_attr"  method="post" action="${pageContext.request.contextPath }/annunci/update" novalidate="novalidate" class="row g-3">
								<form:hidden path="id"/>
								
										<div class="col-md-6">
									<label for="testoAnnuncio" class="form-label">Testo Annuncio <span class="text-danger">*</span></label>
									<spring:bind path="testoAnnuncio">
									<input type="hidden" value="${edit_annunci_attr.testoAnnuncio }" name="testoAnnuncio">
									<textarea name="testoAnnuncio" id="testoAnnuncio" rows="2" cols="20" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire il testo descrizione" required>${edit_annunci_attr.testoAnnuncio }</textarea>
									</spring:bind>
									<form:errors  path="testoAnnuncio" cssClass="error_field" />
								</div>
								
								<div class="col-md-6">
									<label for="prezzo" class="form-label">Prezzo <span class="text-danger">*</span></label>
									<spring:bind path="prezzo">
									<input type="number" name="prezzo" id="prezzo" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire il prezzo" value="${edit_annunci_attr.prezzo }" required>
									</spring:bind>
									<form:errors  path="prezzo" cssClass="error_field" />
								</div>
							
								<div class="col-12">	
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								</div>
		
							</form:form>
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>	
			
			<!-- end container -->	
			</div>	
		</main>
		<jsp:include page="../footer.jsp" />
		
	</body>
</html>