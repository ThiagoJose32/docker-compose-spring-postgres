// Function responsible for querying the GET endpoint
async function buscarMensagem() {
    try{
        const response = await fetch('/api/mensagem');
        const data = await response.text();
        document.getElementById('result-get').innerText = data
    }catch (error) {
        alert("Error fetching message: + error);
    }
}

// Function responsible for querying the POST endpoint
async function enviarDados () {
    const input = document.getElementById("dados-input").value;
    if(!input) return alert("Digite algo");

    try{
        const response = await fetch('/api/data', {
            method: 'POST',
            headers: {
                'Content-Type': 'text/plain'
            }
            body: input
        });
        const data = await response.text();
        document.getElementById('resultado-post').innerText =
        data;
    }catch(error)
        alert("Error sending data: + error);
    }
}