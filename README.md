# desafio_dojot

1 - codingForFood - projeto onde temos as chamadas em ajax (rest) para resgatar os dados de algum arquivo gerado pelo Wilton.

     2 - powerMeterScheduler - projeto onde temos o scheduler
      
          PowerMeterJob.java - classe onde o wilton vai chamar o script dele (roda a cada 2 segundos)

          FillDojotDeviceJob.java - Classe onde o Adriano vai chamar o client do serviço dele pegando os dados que o Wilton ja gravou no arquivo, enviando os mesmos para o dojot, preenchendo o device que o Vini vai ter criado. Este job roda a cada 4 segundos.

     3 - O servidor usado é o Tomcat 9.0.8 por ser mais leve e comportar a aplicação, pelo menos até o momento.  O servidor tambem é pequeno (cerca de 11 MB apenas), portanto, disponibilizei no mesmo link abaixo, é só copiar, descompactar em alguma pasta e criar uma variavel de ambiente chamada CATALINA_HOME apontando para esta pasta onde vc colocou o tomcat, e acrescentar ela na variavel de ambiente path.. assim %CATALINA_HOME% e tambem %CATALINA_HOME%\bin


     4 - cck7550s_12.pmd - Por enquanto arquivo modelo que deve ser gerado pela ferramenta do Wilton.... o nome é composto do modelo do equipamento + numero do predio onde ele se encontra .pmd (power meter data).


     5 - Importe os projetos como MAVEN PROJECTS, e pode compilar normalmente com mvn clean install...... o artefato gerado .WAR, deve ser deployado na pasta "apache-tomcat-9.0.8\webapps"... Apenas jogue o arquivo .war dentro da pasta webapps do tomcat.

     6 - Para startar o servidor, basta usar o arquivo chamado "startup" na pasta bin do tomcat, ou caso queiram utilizar comando, usem "catalina start" para iniciar ou "catalina stop" para derrubar o servidor...


     7 - Observar no console do servidor startado, os printlns dos JOBs do scheduler funcionando...

     8 - Para acessar a URL do projeto com rest utilize a UTL abaixo.

          http://localhost:8080/codingForFoodService/index.html
   
          Digitando o nome do arquivo (que obvio deverá estar no c: (windows)) que na caixa abaixo aparecerá o conteudo deste arquivo. Este projeto deverá conter caso dê tempo, uma pagina de consolidação e consultas aos dados dos medidores e até mesmo do billing.. criando paineis interativos com dados para observação ou mesmo tomada de decisões.


Qualquer duvida me procurem amanha no cpqd.. ou me chamem no talk


Atenciosamente 
Moisés de Pauli Bonfante
