# payment-challenge
Pequeno conceito de micro serviço. Em desafio de uma API de pagamentos da Wirecard.


# Sobre o Projeto

O Projeto foi inspirado em uma arquitetura de micro serviços simplificada. Sendo assim o desafio foi dividido em duas aplicações.

A Primeira aplicação se chama payment-service e é responsável por se comunicar com o checkout/front-end/terminal da plataforma que irá solicitar o pagamento. Em seguida irá identificar o modelo do pagamento e irá enviar as informações necessárias para que um segundo serviço possa fazer a liberação.

O Segundo serviço é baseado de maneira simplificada uma integração bancaria. Ele não conhece as informações do comprador, apenas processa se o cartão de crédito tem limite o suficiente e executa a transação financeira. Ou registra um número de boleto vinculado ao cliente(Plataforma) que solicitou o pagamento.

Todas as informações são persistidas em um banco relacional  (PostgreSQL). 

Nos pagamentos com cartão de crédito, o limite de crédito de cliente é simulado em tempo de execução de cada pagamento. Gerando um número positivo aleatório com o intervalo máximo de R$10000.

Nos pagamentos com boleto, é gerado um número aleatório referente ao código do boleto e o pagamento ficará com o status de pendente.

Também é possível recuperar as informações de pagamento de um determinado cliente (Plataforma de pagamento).
Ou de compradores, pelo CPF do comprador.

# Tecnologias utilizadas.

Java, Spring boot, MyBatis, Mockito, JUnit, PostgreSQL, Lombok, Swagger e  Docker.

# Executando o projeto.

Para executar o projeto é necessário apenas clonar o repositório atual. 
Comando: 
```
 $git clone https://github.com/MarcosPauloLemos/payment-challenge.git
```

Entrar em seu diretório raiz e digitar:
Comando: 
```
* $docker-compose up
```
*  poderá demorar alguns minutos dependendo da velocidade da conexão.

Em seguida, será executado de forma automática os passos para que a aplicação fique disponível. 
* Passo 1 : Clone da imagem do postgreSQL e execução de script para a criação de estrutura inicial.

* Passo 2 : Clone da imagem do Maven. Vinculação dos arquivos do ambiente host para o container, comando para empacotamento e execução dos testes do serviço payment-authorizer e em seguida o serviço será executado.

* Passo 3 : Similar ao passo 2, mas referente ao projeto do payment-service.

# Exemplos

## Solicitação de pagamento com boleto:
#### rota
```
- POST: http://localhost:18080/payment/receive
```
#### Corpo
```
{
    "clientId": 1,
    "buyer": {
        "name": "Marcos Lemos",
        "email": "marcospaulolemos0@gmail.com",
        "cpf": 12365123123
    },
    "amount": 500.5,
    "typeCod": 2
}
```
#### Retorno
```
{
    "authorizerNumber": 5,
    "status": "WAITING_PAY",
    "paymentRef": 6867044022890510004
}
```
## Solicitação de pagamento com Cartão de Crédito:
#### rota
```
- POST: http://localhost:18080/payment/receive
```
#### Corpo
```
{
    "clientId": 1,
    "buyer": {
        "name": "Marcos Lemos",
        "email": "marcospaulolemos0@gmail.com",
        "cpf": 12365123123
    },
    "cardDTO":{
    	"holderName": "MARCOS P S LEMOS",
    	"number":65487894123,
    	"expirationDate": "2023-11-07",
    	"cvv": 123
    },
    "amount": 1340.31,
    "typeCod": 1
}
```
#### Retorno
```
{
    "authorizerNumber": 34,
    "status": "APPROVED",
    "paymentRef": 65487894123
}
```
## Consultar pagamentos de um determinado cliente (Plataforma):
#### rota
```
- GET: http://localhost:18080/payment/payments/client/{cliend-id}
```
obs: para {cliend-id} = 1
#### Retorno
```
[{
        "id": 44,
        "clientId": 1,
        "buyer": {
            "id": 1,
            "name": "Marcos Lemos",
            "email": "marcospaulolemos0@gmail.com",
            "cpf": 1236547898
        },
        "boletoNumber": 6867044022890510004,
        "amount": 500.5,
        "authorizerNumber": 5,
        "typeCod": 2,
        "statusCod": 4,
        "type": "BOLETO",
        "status": "WAITING_PAY"
    },{
        "id": 48,
        "clientId": 1,
        "buyer": {
            "id": 1,
            "name": "Marcos Lemos",
            "email": "marcospaulolemos0@gmail.com",
            "cpf": 1236547898
        },
        "cardNumber": 65487894123,
        "amount": 1340.31,
        "authorizerNumber": 34,
        "typeCod": 1,
        "statusCod": 1,
        "type": "CREDIT_CARD",
        "status": "APPROVED"
    }]
```
## Consultar pagamentos de um determinado comprador:
#### rota
```
- GET: http://localhost:18080/payment/payments/buyer/{cpf}
```
obs: para {cpf} = 1236547898
#### Retorno
```
[{
        "id": 44,
        "clientId": 1,
        "buyer": {
            "id": 1,
            "name": "Marcos Lemos",
            "email": "marcospaulolemos0@gmail.com",
            "cpf": 1236547898
        },
        "boletoNumber": 6867044022890510004,
        "amount": 500.5,
        "authorizerNumber": 5,
        "typeCod": 2,
        "statusCod": 4,
        "type": "BOLETO",
        "status": "WAITING_PAY"
    },{
        "id": 48,
        "clientId": 1,
        "buyer": {
            "id": 1,
            "name": "Marcos Lemos",
            "email": "marcospaulolemos0@gmail.com",
            "cpf": 1236547898
        },
        "cardNumber": 65487894123,
        "amount": 1340.31,
        "authorizerNumber": 34,
        "typeCod": 1,
        "statusCod": 1,
        "type": "CREDIT_CARD",
        "status": "APPROVED"
    }]
```

## Mais informações
Para mais informações ou importação dos exemplos utilizar a documentação disponíveis nos links
(é necessário estar com a aplicação em execução):
#### Para payment-service: http://localhost:18080/swagger-ui.html
#### Para authorizer-service: http://localhost:18081/swagger-ui.html
