async function submitMood() {
    const userInput = document.getElementById('userInput').value;
    const response = await fetch('http://localhost:8080/analyze', {
        method: 'POST',
        headers: { 'Content-Type': 'text/plain' },
        body: userInput
    });

    const data = await response.json();
    const sentiment = data.sentiment;

    document.getElementById('result').textContent = `Your sentiment: ${sentiment}`;
    updateHistory(userInput, sentiment);
}

function updateHistory(input, sentiment) {
    const historyList = document.getElementById('history');
    const listItem = document.createElement('li');
    listItem.textContent = `${input} - ${sentiment}`;
    historyList.appendChild(listItem);
}
