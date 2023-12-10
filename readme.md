<h1 align="center">
  <img src="./logo.svg" width="auto" height="200" alt="PharmaSol by Luís Felipe Ogawa"></img><br><br>
   PharmaSol
<br>
<br>

![GitHub last commit](https://img.shields.io/github/last-commit/lfogawa/pharmasolbackend)

</h1>
<br>

## Summary
<br>

* [Built with](#built-with) | Construído com
* [Description](#description) | Descrição
* [Funcionalities](#funcionalities) | Funcionalidades
* [Improvements possibilities](#improvements-possibilities) | Possibilidades de melhoria
* [Video of the project](#video-of-the-project) | Vídeo do projeto
* [Access the project](#access-the-project) | Acesse o projeto
* [Developers](#developers) | Desenvolvedores

<br>



<br>

## Built with

<br>

![Vite](https://img.shields.io/badge/vite-%23646CFF.svg?style=for-the-badge&logo=vite&logoColor=white)
![Java]()
![Spring]
![PostgreSQL]
![Maven]
<br>

<p align="right">(<a href="#summary">back to top | voltar ao topo</a>)</p>

<br>

## Description
<br>
<p>
  EN-US
</p>

<p align="justify">
The "PharmaSol" system, short for "pharmacy solutions," was created with the aim of assisting pharmaceutical companies in managing data related to pharmacies, stocks, and medicines. The technologies used (Java, Spring, Maven, and PostgreSQL) were intended to facilitate the administration and maintenance of this information, coupled with good performance.<br>

In the system, it is possible to:
initiate the database with pre-existing data for pharmacies, medicines, and stocks;<br>
regarding pharmacies: query them (all and by CNPJ) and include them;<br>
regarding medicines: query them and include them;<br>
regarding stocks: query, acquire, and sell medicines from a particular pharmacy, as well as exchange medicines between the stocks of two pharmacies.<br>
</p>

<br>

<p>
  PT-BR
</p>

<p align="justify">
O sistema "PharmaSol", abreviação de "soluções para farmácias", foi criado com o objetivo de auxiliar empresas farmacêuticas na gestão de dados envolvendo farmácias, estoques e medicamentos. As tecnologias utilizadas (Java, Spring, Maven e PostgreSQL) tiveram como finalidade realizar uma administração e uma manutenção facilitada dessas informações, aliada a uma boa perfomance.<br>

No sistema, é possível:<br>
iniciar o banco com dados prévios de farmácias, medicamentos e estoques; <br>
quanto às farmácias: consultá-las (todas e pelo CNPJ) e incluí-las; <br>
quanto aos medicamentos: consultá-los e incluí-los;<br>
quanto aos estoques: consultar, adquirir e vender medicamentos de determinada farmácia, assim como trocar medicamentos entre os estoques de duas farmácias.<br>
</p>

<p align="right">(<a href="#summary">back to top | voltar ao topo</a>)</p>

<br>

## Funcionalities

<br>

<p>
  EN-US
</p>

📌 Initial Data Load.<br>
✔️ Service responsible for populating initial data into tables.<br>
Endpoint: HTTP POST ⇒ /initialization<br>
Request: no request body or parameters<br>
Response: HTTP 200 OK (no response body)<br>
<br>

📌 Pharmacy Query.<br>
Endpoint: HTTP GET ⇒ /pharmacies<br>
Request: no request body or parameters<br>
Response: HTTP 200 OK, with a list of pharmacies<br>
Successful response fields: cnpj, companyName, tradingName, email, landlineCellphone, cellphone, and address, where the address contains fields: zipCode, street, number, neighborhood, city, state, complement, latitude, longitude<br>
<br>

📌 Pharmacy Query by CNPJ.<br>
✔️ Pharmacy query service by its CNPJ.<br>
Endpoint: HTTP GET ⇒ /pharmacies/{cnpj}<br>
Request: no request body<br>
Responses:<br>
HTTP 200 OK, with pharmacy data if the CNPJ exists<br>
HTTP 404 NOT FOUND, if there is no record with the provided CNPJ, along with an informative error message<br>
Successful response fields: cnpj, companyName, tradingName, email, landlineCellphone, cellphone, and address, where the address contains fields: zipCode, street, number, neighborhood, city, state, complement, latitude, longitude<br>

<br>
📌 Pharmacy Inclusion.<br>
✔️ New pharmacy registration service.<br>
Endpoint: HTTP POST ⇒ /pharmacies<br>
Request:<br>
Request body fields: cnpj, companyName, tradingName, email, landlineCellphone, cellphone, and address, where the address contains fields: zipCode, street, number, neighborhood, city, state, complement, latitude, longitude<br>
Responses:<br>
HTTP 201 CREATED, with pharmacy data in the response body in case of success<br>
HTTP 400 BAD REQUEST, in case of validation errors or an already registered CNPJ, along with an informative error message<br>
Successful response fields: same fields as the request.<br>

<br>
📌 Medicines Query.<br>
✔️ Query service for all medicines registered in the system.<br>
Endpoint: HTTP GET ⇒ /medicines<br>
Request: no request body or parameters<br>
Response: HTTP 200 OK, with a list of medicines<br>
<br>

📌 Medication Inclusion.<br>
✔️ New medication registration service.<br>
Endpoint: HTTP POST ⇒ /medicines<br>
Request:<br>
Request body fields: registerNumber, name, laboratory, dosage, description, price, medicineType<br>
Responses:<br>
HTTP 201 CREATED, with pharmacy data in the response body in case of success<br>
HTTP 400 BAD REQUEST, in case of validation errors or an already registered Register Number, along with an informative error message<br>
Successful response fields: same fields as the request<br>

📌 Pharmacy Stock Query.<br>
✔️ Query service for the stock of medicines in a specific pharmacy.<br>
Endpoint: HTTP GET ⇒ /stocks/{cnpj}<br>
Request: no request body or parameters<br>
Response: HTTP 200 OK, with a list of medicines in stock at that pharmacy<br>
Response fields: list with registerNumber, name, quantity, updateDate<br>

<br>
📌 Acquisition of Medicines for Pharmacy Stock.<br>
✔️ Inclusion service for medicines in the pharmacy's stock. When the pharmacy purchases medicines from suppliers, the respective quantities must be added to the stock.<br>
Endpoint: HTTP POST ⇒ /stocks<br>
Request:<br>
Request body fields: cnpj, registerNumber, quantity<br>
Responses:<br>
HTTP 200 OK, with updated stock data in case of success<br>
HTTP 400 BAD REQUEST, in case of validation errors or failure in any business rule mentioned above, along with an informative error message.<br>
Successful response fields: cnpj, registerNumber, quantity, updateDate<br>

<br>
📌 Sale of Medicines with Pharmacy Stock Update.<br>
✔️ Service for recording the sale of medicines with the necessary update in the pharmacy's stock. When the pharmacy sells medicines to customers, the respective quantities must be reduced from the stock.<br>
Endpoint: HTTP DELETE ⇒ /stocks<br>
Request:<br>
Request body fields: cnpj, registerNumber, quantity<br>
Responses:<br>
HTTP 200 OK, with updated stock data in case of success<br>
HTTP 400 BAD REQUEST, in case of validation errors or in case of CNPJ or Register Number not registered, or failure in any business rule mentioned above, along with an informative error message.<br>
Successful response fields: cnpj, registerNumber, quantity, updateDate<br>

<br>
📌 Exchange of Medicines between Pharmacy Stocks.<br>
✔️ Service to record the exchange of medicines between pharmacies, to balance their stocks when necessary. The exchange of medicines involves decreasing the quantity of a particular medicine from the source pharmacy and adding this quantity to the destination pharmacy. The stocks of both pharmacies must be updated and reflect the final quantity after the exchange.<br>
Endpoint: HTTP PUT ⇒ /stocks<br>
Request:<br>
Request body fields: cnpjOrigin, cnpjDestiny, registerNumber, quantity<br>
Responses:<br>
HTTP 200 OK, with updated stock data in case of success<br>
HTTP 400 BAD REQUEST, in case of validation errors or if CNPJ or Register Number are not found, or failure in any business rule mentioned above, along with an informative error message<br>
Successful response fields: registerNumber, cnpjOrigin, quantityOrigin, cnpjDestiny, quantityDestiny<br>

<br>
<br>

<p>
  PT-BR
</p>

📌 Carga inicial de dados.<br>
✔️ Serviço responsável por popular dados iniciais nas tabelas. <br>
Endpoint: HTTP POST ⇒ /initialization<br>
Request: Sem request body ou parâmetros<br>
Response: HTTP 200 OK (sem response body)<br>
<br>

📌 Consulta de Farmácias.<br>
Endpoint: HTTP GET ⇒ /pharmacies<br>
Request: Sem request body ou parâmetros<br>
Response: HTTP 200 OK, com lista das pharmacies<br>
Campos de response com sucesso: cnpj, companyName, tradingName, email, landlineCellphone, cellphone e address, onde endereco contêm campos: zipCode, street, number, neighborhood, city, state, complement, latitude, longitude<br>
<br>

📌 Consulta de Farmácia pelo CNPJ .<br>
✔️ Serviço de consulta de farmácia pelo seu CNPJ.<br>
Endpoint: HTTP GET ⇒ /farmacias/{cnpj}<br>
Request: Sem request body<br>
Responses:<br>
HTTP 200 OK, com dados da farmácia se CNPJ existente<br>
HTTP 404 NOT FOUND, caso não tenha registro com CNPJ informado, além de mensagem de erro informativa<br>
Campos de response com sucesso: cnpj, companyName, tradingName, email, landlineCellphone, cellphone e address, onde endereco contêm campos: zipCode, street, number, neighborhood, city, state, complement, latitude, longitude<br>

<br>

📌 Inclusão de Farmácia.<br>
✔️ Serviço de cadastro de nova farmácia.<br>
Endpoint: HTTP POST ⇒ /pharmacies<br>
Request:<br>
Campos do request body: cnpj, companyName, tradingName, email, landlineCellphone, cellphone e address, onde endereco contêm campos: zipCode, street, number, neighborhood, city, state, complement, latitude, longitude<br>
Responses:<br>
HTTP 201 CREATED, com dados da farmácia no corpo da resposta em caso de sucesso<br>
HTTP 400 BAD REQUEST, caso haja erros de validação ou de CNPJ já cadastrado, além de mensagem de erro informativa do erro<br>
Campos de response com sucesso: mesmos campos da request.<br>

<br>

📌 Consulta de Medicamentos.<br>
✔️ Serviço de consulta de todos os medicamentos cadastrados no sistema.<br>
Endpoint: HTTP GET ⇒ /medicines<br>
Request: Sem request body ou parâmetros<br>
Response: HTTP 200 OK, com lista dos medicamentos<br>
<br>

📌 Inclusão de Medicamento.<br>
✔️ Serviço de cadastro de novo medicamento.<br>
Endpoint: HTTP POST ⇒ /medicines<br>
Request:<br>
Campos do request body: registerNumber, name, laboratory, dosage, description, price, medicineType<br>
Responses:<br>
HTTP 201 CREATED, com dados da farmácia no corpo da resposta em caso de sucesso<br>
HTTP 400 BAD REQUEST, caso haja erros de validação ou de Register Number já cadastrado, além de mensagem de erro informativa do erro<br>
Campos de response com sucesso: mesmos campos da request<br>

📌 Consulta de Estoque de Farmácia.<br>
✔️ Serviço de consulta do estoque de medicamentos de determinada farmácia.<br>
Endpoint: HTTP GET ⇒ /stocks/{cnpj}<br>
Request: Sem request body ou parâmetros<br>
Response: HTTP 200 OK, com lista dos medicamentos em estoque naquela farmácia<br>
Campos de response: lista com registerNumber, name, quantity, updateDate<br>

<br>

📌 Aquisição de Medicamentos para Estoque de farmácia.<br>
✔️ Serviço de inclusão de medicamentos no estoque da farmácia. Quando a farmácia faz a compra de medicamentos dos fornecedores, devem ser adicionadas as respectivas quantidades no estoque.<br>
Endpoint: HTTP POST ⇒ /stocks<br>
Request:<br>
Campos do request body: cnpj, registerNumber, quantity<br>
Responses:<br>
HTTP 200 OK, com dados do estoque atualizados em caso de sucesso<br>
HTTP 400 BAD REQUEST, caso haja erros de validação, ou falha em alguma regra de negócio prevista acima, além de mensagem de erro informativa do erro.<br>
Campos de response com sucesso: cnpj, registerNumber, quantity, updateDate<br>

<br>

📌 Venda de Medicamentos com atualização do Estoque de farmácia.<br>
✔️ Serviço de lançamento de venda de medicamentos com a devida atualização no estoque da farmácia. Quando a farmácia faz a venda de medicamentos aos clientes, devem ser reduzidas as respectivas quantidades no estoque.<br>
Endpoint: HTTP DELETE ⇒ /stocks<br>
Request:<br>
Campos do request body: cnpj, registerNumber, quantity<br>
Responses:<br>
HTTP 200 OK, com dados do estoque atualizados em caso de sucesso<br>
HTTP 400 BAD REQUEST, caso haja erros de validação ou em caso de em caso de CNPJ ou Register Number não cadastrados, ou ainda em caso de falha em alguma regra de negócio prevista acima, além de mensagem de erro informativa do erro.<br>
Campos de response com sucesso: cnpj, registerNumber, quantity, updateDate<br>

<br>

📌 Troca de Medicamentos entre Estoques de Farmácias.<br>
✔️ Serviço para registrar a troca de medicamentos entre as farmácias, para equilibrar os estoques entre elas quando necessário. A troca de medicamento consiste na diminuição na quantidade de determinado medicamento da farmácia origem e adição desta quantidade na farmácia destino. O estoque de ambas devem ser atualizados e refletir a quantidade final após a troca.<br>
Endpoint: HTTP PUT ⇒ /stocks<br>
Request:<br>
Campos do request body: cnpjOrigin, cnpjDestiny, registerNumber, quantity<br>
Responses:<br>
HTTP 200 OK, com dados dos estoques atualizados em caso de sucesso<br>
HTTP 400 BAD REQUEST, caso haja erros de validação ou em caso de em caso de CNPJ ou Register Number não localizados, ou ainda em caso de falha em alguma regra de negócio prevista acima, além de mensagem de erro informativa do erro<br>
Campos de response com sucesso: registerNumber, cnpjOrigin, quantityOrigin, cnpjDestiny, quantityDestiny<br>

<br>
<br>


<p align="right">(<a href="#summary">back to top | voltar ao topo</a>)</p>

<br>

## Improvements possibilities

<br>

<p>
  EN-US
</p>

🔨 Implement authentication and authorization with Spring Security and JWT Tokens<br>
🔨 Implement documentation with Apache Log4j<br>
🔨 Implement pharmacy deletion functionality<br>
🔨 Implement medicine deletion functionality<br>
🔨 Thoroughly review the code, ensuring alignment with best practices and addressing it<br>
<br>

<p>
  PT-BR
</p>

🔨 Implementar autenticação e autorização com Spring Security e tokens JWT<br>
🔨 Implementar documentação com apache log4j<br>
🔨 Implementar a funcionalidade de deleção de farmácia<br>
🔨 Implementar a funcionalidade de deleção de medicamento<br>
🔨 Revisar profundamente o código, analisando se está de acordo com as boas práticas e corrigindo-o<br>
<br>

<p align="right">(<a href="#summary">back to top | voltar ao topo</a>)</p>

<br>

## Video of the project

<br>

* Video link: https://drive.google.com/file/d/1i_M9UdhBOSgmTry-hXudhM05v8a1zap5/view?usp=sharing

<p align="right">(<a href="#summary">back to top | voltar ao topo</a>)</p>

<br>

## Access the project

<br>

* Github link (private project): https://github.com/lfogawa/pharmasolbackend

<p align="right">(<a href="#summary">back to top | voltar ao topo</a>)</p>

<br>

## Developers

<br>

<div align="center">

| <img src="https://avatars.githubusercontent.com/u/94766274?s=400&u=6f60eb332344c8284ad28ed4e240522e4cc35e0e&v=4" width=115><br>Luís Felipe Ogawa|
| :---: |

Github: https://github.com/lfogawa <br>
LinkedIn: https://www.linkedin.com/in/lu%C3%ADs-felipe-ogawa/ <br>

</div>

<p align="right">(<a href="#summary">back to top | voltar ao topo</a>)</p>