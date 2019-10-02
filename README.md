# lab1chat
Servicio de chat, primer lab de redes.

Para clonar:
````
git clone https://github.com/HogoK/lab1chat.git
````
Para subir cambios:
````
git push
````
Para actualizar cambios locales:
````
git pull
````

Otros:
````
Como preparar VScode para Java: https://www.youtube.com/watch?v=bKwcFo62cnI


Enunciado Laboratorio 1: 

- Construir sistema mensajería vía socket, con 1 servidor y varios clientes. [TERMINADO]

- Construir en los lenguajes indicados un sistema de Chat en el cual exista un servidor al que se puedan conectar varios clientes. [TERMINADO]

- Al ejecutar el programa Servidor, deberá pedir el puerto (y la dirección IP de la interfaz de red en caso de ser varias) en el cual escuchará las conexiones (donde se abrirá el socket).

- Al conectarse un cliente, se debe pedir la dirección ip (o nombre de dominio) y puerto del servidor, tras lo cual se conectará a dicho servidor. 

- Además, deberá pedir un nombre de usuario o Nick para identificarlo. [TERMINADO]

- Por el lado del servidor, cuando un cliente se conecte se debe mostrar en la consola el detalle de dicho evento: IP y nombre del cliente [TERMINADO] (FALTA NOMBRE)

- además de la fecha/hora. [TERMINADO]

- En los otros Clientes, debe aparecer la misma notificación de que el usuario en cuestión se ha conectado. [TERMINADO]

- Cuando un Cliente esté conectado, deberá poder enviar mensajes por la consola los cuales llegarán a todos los otros Clientes. 

- A dichos clientes deberá mostrarse el mensaje en pantalla indicando la fecha/hora y el nombre del usuario que envió el mensaje, 
además del mensaje en sí. [TERMINADO]

- En el servidor, deben mostrarse en pantalla de la misma forma, todos los mensajes que viajen por el sistema.[TERMINADO]

- El sistema debe permitir desconectarse con algún menú o combinación de teclas especial (por ejemplo, al escribir “quit” o “salir” 
u otro mensaje especial). Al desconectarse, en los otros Clientes y en el servidor, debe aparecer una notificación de que el usuario 
en cuestión se ha desconectado. El Cliente, al ejecutar nuevamente el programa Cliente, deberá poder re-conectarse al chat y realizar 
todo tal cual como al inicio. [TERMINADO] (FALTA MENSAJE DE DESCONEXION)

- Agregar tolerancia a fallos con un segundo servidor de tal forma que cuando se “caiga” el servidor, los clientes deben recibir una 
notificación de este evento y pedir al usuario la IP y puerto de un segundo servidor (que ya debe estar ejecutándose). Tras esto, 
se deben re-conectar a este nuevo servidor.

- En el segundo servidor, deben verse los mismos eventos por la consola, esto es quienes se conectan, fecha/hora dirección IP, etc. 

- Si el segundo servidor falla, debe volver a pedir los datos de un nuevo servidor.

````