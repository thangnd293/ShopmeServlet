async function postData(url = "", data = {}) {
  const response = await fetch(url, {
    method: "POST",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  return response.json();
}

postData("http://localhost:8080/be_java_web/api/v1/user", { age: "42" }).then(
  (data) => {
    console.log(data);
  }
);
