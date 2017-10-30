
/* global i */

// Funciones particulares por cada furmulario
function miFormulario()
   {
   //return true;
   //var el=document.getElementById( "capa");
   //document.getElementById("capa").innerHTML = "ADIOS";
   // Primero quitamos el contenido
   //while( el.firstChild!=null ) {
   //el.removeChild( el.firstChild );
   
   
   //el.appendChild("label");
   //var nodo=document.getElementById( "intro" )
      
   //el.createElement("p");
   //el.createTextNode("Texto de prueba...");
 
   //CÓDIGO DE PRUEBA   
   var el=document.getElementById( "capa");
   el.innerHTML += "Text to append";
   el.style.color="red"
   var x = document.createElement("p");                        // Create a <p> node
   var t = document.createTextNode("This is a paragraph.");    // Create a text node
   el.appendChild(t);
   el.appendChild(x);                                 // Append the text to <p>
   //document.body.appendChild(x);                             // Append <p> to <body>
   //el.document.appendChild(x);                                 // Append the text to <p>
   //FIN CÓDIGO DE PRUEBA
   var f = document.getElementById("id_form");
   var fx = document.createElement("p");
   var ft = document.createTextNode("Probando campo:");
 
   var hiddenField = document.createElement("input");
   hiddenField.setAttribute("type", "text");
   hiddenField.setAttribute("name", "key");
   hiddenField.setAttribute("value", "prueba");
   
   f.appendChild(ft);
   f.appendChild(fx);
   
   f.appendChild(hiddenField);
   
  }
  
  //Función para el fomulariomde Inscripciones.  
  function frm_inscrip()
   {
   // Declaración de variables
   var tabla = document.getElementById("id_table");

   // Primero quitamos el contenido
   while( tabla.firstChild!=null ) {
   tabla.removeChild( tabla.firstChild ); }

   // Etiquetas
   var txt_ced = document.createTextNode("Cedula:");
   var txt_nom = document.createTextNode("Nombres:");
   var txt_ape = document.createTextNode("Apellidos:");
   var txt_per = document.createTextNode("Periodo:");
   var txt_tur = document.createTextNode("Turno:");
   
   // Entradas
   var cmp_ced = document.createElement("input");
   var cmp_nom = document.createElement("input");
   var cmp_ape = document.createElement("input");
   var cmp_per = document.createElement("input");
   //var cmp_tur = document.createElement("input");
   // Entrada de lista de turnos
   var selectListTurno = document.createElement("select");   
	var arregloTurno = ["Matutino","Vespertino","Nocturno","Sabatino"];


   // comandos Botones
   var btn_guarda = document.createElement("button");        // Create a <button> element
   var btn_cancel = document.createElement("button");        // Create a <button> element
   var btn_buscar = document.createElement("button");        // Create a <button> element

   var tbt_guarda = document.createTextNode("Guardar");       // Create a text node
   var tbt_cancel = document.createTextNode("Limpiar");       // Create a text node
   var tbt_buscar = document.createTextNode("Buscar");       // Create a text node
   
   // Insertando registros a la tabla
   var reg0 = tabla.insertRow(0);
   var reg1 = tabla.insertRow(1);
   var reg2 = tabla.insertRow(2);
   var reg3 = tabla.insertRow(3);
   var reg4 = tabla.insertRow(4);
   var reg5 = tabla.insertRow(5);
   
   //var x = tabla.insertCell(0);
   var cel_txt_ced = reg0.insertCell(0);
   var cel_cmp_ced = reg0.insertCell(1);

   var cel_txt_nom = reg1.insertCell(0);
   var cel_cmp_nom = reg1.insertCell(1);

   var cel_txt_ape = reg2.insertCell(0);
   var cel_cmp_ape = reg2.insertCell(1);

   var cel_txt_per = reg3.insertCell(0);
   var cel_cmp_per = reg3.insertCell(1);

   var cel_txt_tur = reg4.insertCell(0);
   var cel_cmp_tur = reg4.insertCell(1);

   var cel_btn_guarda = reg5.insertCell(0);
   var cel_btn_cancel = reg5.insertCell(1);
   //var cel_btn3 = reg4.insertCell(1);
   

   // Asignando propiedades
   cmp_ced.setAttribute("type", "text");
   cmp_ced.setAttribute("name", "cedula");
   cmp_ced.setAttribute("value", "");
   cmp_ced.setAttribute("maxlength","8");
   cmp_ced.setAttribute("onkeypress","return numbersonly(event)");
   
   cmp_nom.setAttribute("type", "text");
   cmp_nom.setAttribute("name", "nombres");
   cmp_nom.setAttribute("value", "");
   
   cmp_ape.setAttribute("type", "text");
   cmp_ape.setAttribute("name", "apellidos");
   cmp_ape.setAttribute("value", "");

   cmp_per.setAttribute("type", "number");
   cmp_per.setAttribute("name", "periodo");
   cmp_per.setAttribute("value", "");

   //cmp_tur.setAttribute("type", "number");
   //cmp_tur.setAttribute("name", "turno");
   //cmp_tur.setAttribute("value", "");
   //tabla.appendChild(selectListTurno);
   
   //Insertando Valores
    for (var i = 0; i < arregloTurno.length; i++) {
    	var optionTurno = document.createElement("option");
    	optionTurno.value = arregloTurno[i];
    	optionTurno.text = arregloTurno[i];
    	selectListTurno.appendChild(optionTurno);
    }   

    // Instanciando botones	   
    btn_guarda.appendChild(tbt_guarda); 
    btn_cancel.appendChild(tbt_cancel);
    btn_buscar.appendChild(tbt_buscar); 
   
    // Cargando celdas 
    cel_txt_ced.appendChild(txt_ced);
    cel_cmp_ced.appendChild(cmp_ced);   

    cel_txt_nom.appendChild(txt_nom);
    cel_cmp_nom.appendChild(cmp_nom);   

    cel_txt_ape.appendChild(txt_ape);
    cel_cmp_ape.appendChild(cmp_ape);   

    cel_txt_per.appendChild(txt_per);
    cel_cmp_per.appendChild(cmp_per);   

    cel_txt_tur.appendChild(txt_tur);
    cel_cmp_tur.appendChild(selectListTurno);   

    cel_btn_guarda.appendChild(btn_guarda);
    cel_btn_cancel.appendChild(btn_cancel);
    cel_cmp_ced.appendChild(btn_buscar);
        
  }
  
// Función para blanquear los campos
function clearThis(target){
        target.value= "";
    }
    
// Función para validar solo campos numéricos    
function checkIt(evt) {
    evt = (evt) ? evt : window.event
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        status = "This field accepts numbers only."
        return false
    }
    status = ""
    return true
}

// Función para validar campos doubles, floats y decimales
function checkDecimal(evt) {
    evt = (evt) ? evt : window.event
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode > 31 && (charCode < 46 || charCode > 57)) {
        status = "This field accepts numbers only."
        return false
    }
    status = ""
    return true
}

//Función para validar la contraseña segura
function validarContrasena(input) {    
    //alert(document.getElementById("password1").value);
    var cadena = input.value.toString();
    var letraMayuscula = false;
    var letraMinuscula = false
    var numerico = false;
    var caracterEspecial = false;
    var longitudPermitida = false;
    
    //@#=?+*_%/.>:
    if (cadena.length>=8) {
        longitudPermitida = true;
        for (var i=0;i<cadena.length;i++) {
            var caracter = cadena[i];
         
            //Valida si es numérico
            if (/^([0-9])*$/.test(caracter)) {
                numerico = true;
            } else {            
                //Valida si es un caracter especial
                if (/^([@#=?+*_%/.>:])*$/.test(caracter)) {
                    caracterEspecial = true;
                } else { 
                    //Valida si es Mayúscula            
                    if (caracter == caracter.toUpperCase()) {                
                        letraMayuscula = true;                        
                    } else {
                        //Valida se es Minúscula
                        if (caracter == caracter.toLowerCase() ) {                
                            letraMinuscula = true;
                        }
                    }
                }
            }
        }
    } else {
        input.setCustomValidity('Mínima de 8 caracteres');
        //return false;
    }
    if (letraMayuscula && letraMinuscula && numerico && caracterEspecial && longitudPermitida) {
        input.setCustomValidity('');
        //return true;
    } else {
        //return false;
        input.setCustomValidity('Contraseña débil');
    }
}

//Función para validar la confirmación de la contraseña
function checkConfirm(input) {    
    //alert(document.getElementById("password1").value);
    if (input.value != document.getElementById("campoConfirm").value) {
        input.setCustomValidity('No Coincide');
        //return false;
    } else {
        // input is valid -- reset the error message
        //input.setCustomValidity('Contraseña Confirmada');
        input.setCustomValidity('');
        //return true;
    }
}	

function nobackbutton(){
  //alert("nobackbutton");
  window.location.hash="no-back-button";
  window.location.hash="Again-No-back-button" //chrome
  window.onhashchange=function(){window.location.hash="no-back-button";}
}

//Función para mostrar el aula y horario en la tabla de inscripción.
function funSeccion(sel) {
    var ind = 0;
    var indice = sel.selectedIndex; // Capturando la posición de la opción
    var txtSeccion = sel.options[indice].text; // Seleccionando el texto de la opción o sección
    var valSeccion = sel.options[indice].value; // Seleccionando el value de la opción o sección
    var codMat = sel.value; // Captura el nombre completo del Select
    codMat = codMat.substring(0,6); // Corta solo los caracteres del código de la materia
    
    var aulaHorarios = "";
    
    arregloDeSubCadenas = valSeccion.split('|');
    var nombEtiqueta = arregloDeSubCadenas[0]; // Nombre de Etiqueta la etiqueta del Label
    var aulaHorarios = arregloDeSubCadenas[1];
    
    //alert(arregloDeSubCadenas[0]);
    //alert(nombEtiqueta);
    aulaHorarios = arregloDeSubCadenas[1];
    //Insertando Valores
    for (var i = 2; i < arregloDeSubCadenas.length; i++) {
    	aulaHorarios = aulaHorarios + "\n"+ arregloDeSubCadenas[i];
    }

    document.all(nombEtiqueta).innerText = aulaHorarios;
}

// Función para deshabilitar el select option para permitir seleccionar la seccion.
function deshabilitaSeccion(sel) {   
    if(document.getElementById(sel.value).checked) {
        document.getElementById("sec"+sel.value).disabled=false;
    } else {
        document.getElementById("sec"+sel.value).disabled=true;
    }
}

// Grupo de funciones del Timeout
// Funcion para el manejo del timeout de Session del usuario
var timeoutHandle = null;
function startTimer(timeoutCount) {
    if (timeoutCount == 0) {
        //window.location.href = 'logout.jsp';
        alert("ATENCI\u00D3N...! \n Esta Sesi\u00F3n no ha tenido Actividad. \n Quedan menos de 30 Segundos para que la misma expire. \n Favor ejecute cualquier acci\u00F3n necesaria para mantenese en sesi\u00F3n, \n de lo contrario ignore este mensaje.");
        //refreshTimer();
        //timeoutCount=270; 
    } else if (timeoutCount <= 30) {
        document.getElementById('sessionTimer').innerHTML = 'Restan ' + (timeoutCount * 2000)/1000 + ' segundos para finalizar la sessi&oacuten.' ;
    }
    //alert(timeoutCount);
    timeoutHandle = setTimeout(function () { startTimer(timeoutCount-30);}, '30000');
}

// Función para refrescar y reiniciar el conteo del reloj
// Aún sin utilizar
function refreshTimer() {
    killTimer(timeoutHandle);
    startTimer(270);
}
// Fin del grupo de Funciones