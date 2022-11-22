<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Ricerca</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Ricerca elementi</h5> 
				    </div>
				    <div class='card-body'>
		
		
							<form method="post" action="${pageContext.request.contextPath}/annunci/public/list" class="row g-3" >
							
							
								<div class="col-md-6">
									<label for="prezzo" class="form-label">Prezzo</label>
									<input type="text" name="prezzo" id="prezzo" class="form-control" placeholder="Inserire il prezzo"  >
								</div>
								
								<div class="col-md-6">
									<label for="utenteInserimento" class="form-label">Utente che ha pubblicato L'annuncio</label>
									<input type="hidden" name="utenteId" id="utenteId">
									<input type="text" name="utenteInserimento" id="utenteInserimento" class="form-control" placeholder="Inserire il l'utente che ha pubblicato l'annuncio"  >
								</div>
								
								<%-- FUNZIONE JQUERY UI PER AUTOCOMPLETE --%>
								<script>
									$("#utenteInserimento").autocomplete({
										 source: function(request, response) {
										        $.ajax({
										            url: "../utente/searchUtenteAjax",
										            datatype: "json",
										            data: {
										                term: request.term,   
										            },
										            success: function(data) {
										                response($.map(data, function(item) {
										                    return {
											                    label: item.label,
											                    value: item.value
										                    }
										                }))
										            }
										        })
										    },
										//quando seleziono la voce nel campo deve valorizzarsi la descrizione
									    focus: function(event, ui) {
									        $("#utenteInserimento").val(ui.item.label)
									        return false
									    },
									    minLength: 2,
									    //quando seleziono la voce nel campo hidden deve valorizzarsi l'id
									    select: function( event, ui ) {
									    	$('#utenteId').val(ui.item.value);
									    	//console.log($('#registaId').val())
									        return false;
									    }
									});
								</script>
								<!-- end script autocomplete -->	
		
								<div class="col-md-6">
									<label for="categorie" class="form-label">Categorie</label>
									<input type="hidden" name="categorieId" id="categorieId">
									<input type="text" class="form-control" name="categorie" id="categorie" placeholder="Inserire la categoria"  >
								</div>
		
									<%-- FUNZIONE JQUERY UI PER AUTOCOMPLETE --%>
								<script>
									$("#categorie").autocomplete({
										 source: function(request, response) {
										        $.ajax({
										            url: "../categoria/searchCategorieAjax",
										            datatype: "json",
										            data: {
										                term: request.term,   
										            },
										            success: function(data) {
										                response($.map(data, function(item) {
										                    return {
											                    label: item.label,
											                    value: item.value
										                    }
										                }))
										            }
										        })
										    },
										//quando seleziono la voce nel campo deve valorizzarsi la descrizione
									    focus: function(event, ui) {
									        $("#categorie").val(ui.item.label)
									        return false
									    },
									    minLength: 2,
									    //quando seleziono la voce nel campo hidden deve valorizzarsi l'id
									    select: function( event, ui ) {
									    	$('#categorieId').val(ui.item.value);
									    	//console.log($('#registaId').val())
									        return false;
									    }
									});
								</script>
								<!-- end script autocomplete -->	
		
								<div class="col-md-3">
									<label for="data" class="form-label">Data di inserimento</label>
                        			<input class="form-control" id="data" type="date" placeholder="dd/MM/yy"
                            			title="formato : gg/mm/aaaa"  name="data"   >
								</div>
								
							<div class="col-12">
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								<a class="btn btn-outline-primary ml-2" href="${pageContext.request.contextPath}/annunci/insert">Add New</a>
								<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
							</div>
		
						</form>
  
				    
				    
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