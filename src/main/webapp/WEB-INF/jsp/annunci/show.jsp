<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<!-- Common imports in pages -->
	<jsp:include page="../header.jsp" />
	<title>Visualizza elemento</title>
	
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  	<div class="container">
	  	
			<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					
			<div class='card'>
			    <div class='card-header'>
			        Visualizza dettaglio
			    </div>
			
			    <div class='card-body'>
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Id:</dt>
					  <dd class="col-sm-9">${show_annunci_attr.id}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Testo Annuncio:</dt>
					  <dd class="col-sm-9">${show_annunci_attr.testoAnnuncio}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Prezzo:</dt>
					  <dd class="col-sm-9">${show_annunci_attr.prezzo}</dd>
			    	</dl>

			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Data Inserimento:</dt>
					  <dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_annunci_attr.data}" /></dd>
			    	</dl>

			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Utente Inserimento:</dt>
					  <dd class="col-sm-9">${show_annunci_attr.utenteInserimento.nome}${show_annunci_attr.utenteInserimento.cognome}</dd>
			    	</dl>
			    	
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Categorie:</dt>
					  <c:forEach items="${show_annunci_attr.categorie}" var="categoria">
					  <dd class="col-sm-9">${categoria.codice }</dd>
					  </c:forEach>
			    	</dl>
			    	
			    <!-- end card body -->
			    </div>
			    
			    <div class='card-footer'>
			    	<form action="${pageContext.request.contextPath }/annunci/buy" method="post">
			    		<input type="hidden" value="${show_annunci_attr.id }" name="idAnnuncio">
			    		<button type="submit" name="submit" value="submit" id="submit" class="btn btn-success">Acquista</button>
				        <a href="${pageContext.request.contextPath }/annunci" class='btn btn-outline-secondary' style='width:80px'>
				            <i class='fa fa-chevron-left'></i> Back
				        </a>
			        </form>
			    </div>
			<!-- end card -->
			</div>	
	
		<!-- end container -->  
		</div>
		
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>