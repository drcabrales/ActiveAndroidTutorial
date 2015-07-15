# ActiveAndroidTutorial
Tutorial mostrando como desenvolver usando ActiveAndroid na aplicação

#Objetivo

Este tutorial tem como objetivo demonstrar como elaborar requisições básicas de um banco de dados (insert, update, delete e select) criado com auxilio do ActiveAndroid. O exemplo é feito baseado na ideia de um banco de dados de Cursos, onde estes se relacionam com seus professores que os ministram. Tendo isso em mente, algumas funcionalidades foram adicionadas a interface para mostrar na prática todo o funcionamento da API.

#Sobre o ActiveAndroid

O ActiveAndroid, desenvolvido por Michael Pardo (www.michaelpardo.com), é uma alternativa de mapear um banco de dados (desde sua configuração até suas operações) sem a utilização direta de SQLite. Para aqueles que estão acostumados a desenvolver usando Java para web podem notar grandes semelhanças com o Hibernate, pois o ActiveAndroid faz uso de anotações para descrever suas propriedades, tanto das tabelas e colunas como dos relacionamentos entre tabelas. Sua API permite fazer operações básicas de maneira simples e clara, o que torna a vida do desenvolvedor mais fácil.

#Importando a API

Neste projeto, foi feito uso do activeandroid-3.0.jar para importar a API necessária para o desenvolvimento da aplicação. Vale salientar que, como o projeto foi feito no eclipse, bastou importar esse .jar e fazer sua devida utilização no Build Path para ter total acesso aos métodos e anotações. 

#Configurando o banco de dados

Para iniciar, o banco de dados da aplicação deve ser criado. Mas como comentamos anteriormente, isso é feito sem nenhuma linha de código SQLite. E como isso é feito? Bem, para isso precisamos fazer uso de tags no AndroidManifest.xml contendo o valor que define a criação do banco ("AA_DB_NAME"), o nome do banco de dados em si, o valor que define a versão do banco de dados ("AA_DB_VERSION") e a versão do bando de dados. Com isso, o banco de dados será posteriormente criado.

#Configurando a ActiveAndroid Application

Para inicializar o ActiveAndroid é necessário criar uma classe Java que extenda Application (pois essa application será passada para o AndroidManifest.xml) e nela ser feita a devida inicialização fazendo uso do ActiveAndroid.initialize(this), onde this representa a própria aplicação. Dessa forma o setup para criação do banco de dados e sua possível utilização está concluido.

#Criando tabelas

As tabelas são representadas por classes java comuns que extendem a classe Model, possuindo seus atributos. A diferença aqui são as anotações para indicar que de fato as tabelas e colunas serão representadas. No exemplo é possível ver algumas dessas anotações, como as mais utilizadas, que são @table e @column. Intuitivamente, elas representam, respectivamente, a indicação que a classe se trata de uma tabela e os seus atributos se tratam de colunas. Ainda no exemplo podemos ver a utilização de "name", tanto na indicação da coluna como da tabela. Ela indica o nome que será utilizado no banco de dados para aqueles componentes. 
É possível a utilização de outras propriedades mais específicas para determinados casos, e utilizamos uma delas que foi a "onDelete" no atributo professor em Curso. Para o valor "Column.ForeignKeyAction.CASCADE" ela permite que, ao deletar um Curso, o professor relacionado também seja deletado.
Como este tutorial está focado em ensinar o setup e utilização básica de operações de bancos de dados no ActiveAndroid, essas propriedades mais específicas não serão muito abordadas.

#Realizando operações básicas

No exemplo, fizemos uma interface que permite ao usuário trabalhar com 6 funcionalidades diferentes: Salvar curso, Listar todos os cursos, Listar cursos por professor, Deletar todos os dados, Deletar algum curso específicos e Atualizar o professor de algum curso.
Para cada uma dessas funcionalidades, existe um botão para teste. Algumas particularidades da utilização merecem ser ressaltadas:

- Para o listar cursos por professor, o campo de texto professor precisa estar preenchido
- Para o Deletar curso e Atualizar professor de curso, o campo curso tem que estar preenchido (e para o atualizar professor o campo professor também)

No código, é perceptível a facilidade de fazer essas consultas e operações com ActiveAndroid. Por exemplo, para o salvar, basta criar um objeto com as anotações @table e @column e após o devido preenchimento de dados nos atributos basta chamar o save() no objeto. Para o Atualizar, o save() também é utilizado, contanto que ele tenha sido previamente obtido do banco de dados (pois assim o ID é alcançado) e alterado. Para buscar objetos do banco de dados, podem ser feitas consultas via classe Select() (que possui métodos from(), where(), orderBy() e por isso não precisando de nenhuma linha SQLite) ou via load(). Ambos estão exemplificados no projeto. Para deletar, assim como no salvar, basta usar delete() no objeto que deseja ser deletado (desde que esse tenha o ID vindo do banco de dados). 

#Considerações finais

Vale mais uma vez salientar que todo o processo descrito aqui pode ser verificado no projeto, tanto nas classes do package model como no package principal contendo MyApplication e MainActivity e AndroidManifest. O projeto foi feito no Eclipse, e sua conversão para a utilização no Android Studio não foi realizada, logo não recomendamos essa utilização.
