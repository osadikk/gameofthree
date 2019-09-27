document.write('<script type="text/javascript" src="/js/fancyTable.js"></script>');



$(document).ready(function() {
	$("#gameTable").fancyTable({
		sortColumn:0,
		pagination: true,
		perPage:10,
		globalSearch:true
	});		
});
