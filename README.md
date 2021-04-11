# PADV - Procura de Advogados

Este projeto tem como objetivo demonstrar os conhecimentos adquiridos na aula de Mobile através de um app que facilita a procura de profissionais da área jurídica a fim de solucionar algo caso o questão que necessita desse tipo de profissional.

## Estrutura

Como banco de dados foi utilizado o firabase;
Foi utilizada a seguinte estrutura de projeto aprendida em aula para melhor desacoplamento e organização de código:

- Data
  - remote/datasource
    - firebase - implementação da conversa do app com a camada de banco (no caso Firebase);
  - repository - Implementação dos métodos do repositório , no caso chamando o remote dataSource (Firestore);
- Domain
  - entity - Objetos utilizados para transitar as informações;
  - exception - Tratativas de erros customizados;
  - repository - Declaração das funções (interface) que o repositorio vai ter;
  - usecase - use cases com cada funcionalidade a ser usada pelas telas;
- Presentation
  - base e base/auth: Bases criadas como aprendido em aula para controle de telas logadas e não logadas;
  - ... Pacotes que contém o Fragment, ViewModel e ViewModelFactory (camada responsável pela logica da parte visual);

## Workload

Nesta versão inicial é possível criar uma conta, atualizá-la, efetuar a pesquisa de profissionais e efetuar o logout.

- <i><ins>Criar uma conta</isn></i>
  <p>Para criar uma nova conta basta clicar em <strong>Cadastra-se</strong> e preencher as informações solicitadas</p> 
   <div>
      <img src="https://user-images.githubusercontent.com/28123282/114287716-70bd3f80-9a3f-11eb-86cc-cea4d9411a87.png" height="400px"/>
      <img src="https://user-images.githubusercontent.com/28123282/114287744-c85bab00-9a3f-11eb-9e05-a5a0812f3065.png" height="400px"/>
  </div>


- <i><ins>Atualizar conta</ins></i>
  <p>Clicar no icone do usuário, preencher as informações e clicar em <strong>update</strong></p>
   <div>
      <img src="https://user-images.githubusercontent.com/28123282/114288031-5cc70d00-9a42-11eb-93cf-8632c4b725b9.png" height="400px"/>
      <img src="https://user-images.githubusercontent.com/28123282/114287996-11acfa00-9a42-11eb-85d5-992ca120f7a0.png" height="400px"/>
  </div>


- <i><ins>Pesquisa de profissionais</ins></i>
  <p>Digitar o nome do profissional desejado no campo de pesquisa e aguardar o retorno</p>
   <div>
      <img src="https://user-images.githubusercontent.com/28123282/114288031-5cc70d00-9a42-11eb-93cf-8632c4b725b9.png" height="400px"/>
  </div>


- <i><ins>Logout</ins></i>
   <div>
      <img src="https://user-images.githubusercontent.com/28123282/114288264-7cf7cb80-9a44-11eb-8f60-fef023d7afac.png" height="400px"/>
  </div>
