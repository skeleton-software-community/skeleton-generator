function setSelectionRange(input, selectionStart, selectionEnd) {
	if (input.setSelectionRange) {
		input.focus();
		input.setSelectionRange(selectionStart, selectionEnd);
	} else if (input.createTextRange) {
		var range = input.createTextRange();
		range.collapse(true);
		range.moveEnd('character', selectionEnd);
		range.moveStart('character', selectionStart);
		range.select();
	}
}

function setCaretToEnd(e) {
	var input = document.getElementById(e.source.id);
	setSelectionRange(input, input.value.length, input.value.length);
}


function displaySelectUnselectAll() {
	var checkbox = $("input[id$='selectUnselectAll']").get(0);	
	var elements = $("input[id$='selectUnselect']");
	
	bindSelectUnselectAll(checkbox, elements);
	
	if (elements.length == 0) {
		checkbox.checked = false;
		checkbox.style.display = "none";
	}
}

function selectUnselectAll(checkbox) {
	var elements = checkbox.form.elements;
	for ( var i = 0; i < elements.length; i++) {
		var element = elements[i];
		if (/selectUnselect$/.test(element.id)) {
			element.checked = checkbox.checked;
		}
	}
	if (checkbox.checked) {
		showActions();
	} else {
		hideActions();
	}
}

function selectUnselect(arg) {
	checkbox = document.getElementById(arg);
	var elements = checkbox.form.elements;
	
	bindSelectUnselectAll(checkbox, elements);	
}

function bindSelectUnselectAll(checkbox, elements) {
	var allSelected = true;
	var selectedItemNumber = 0;
	for (var elementIndex = 0; elementIndex < elements.length; elementIndex++) {
		var element = elements[elementIndex];
		if (/selectUnselect$/.test(element.id)) {
			if (!element.checked) {
				allSelected = false;
			} else {
				selectedItemNumber++;
			}
		}
	}
	checkbox.checked = allSelected;
	if (selectedItemNumber > 0) {
		showActions();
	} else {
		hideActions();
	}
}

function showActions() {
	document.getElementById('dropList').style.display = "block";
}

function hideActions() {
	document.getElementById('dropList').style.display = "none";
}