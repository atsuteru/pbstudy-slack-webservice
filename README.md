- [x] MarkDownエディタが欲しい - Typoraのインストール

- [ ] Webサービスを作成する - Jessyで。slack認証を行うpathを決めておく
  OpenJDK入手: openjdk-12.0.2_windows-x64_bin.zip
  Maven入手: apache-maven-3.6.1-bin.zip

  ```dosbatch
  C:\Users\teruk\Documents>call %MVN_HOME%\bin\mvn archetype:generate ^
  More? -DarchetypeArtifactId=jersey-heroku-webapp ^
  More? -DarchetypeGroupId=org.glassfish.jersey.archetypes ^
  More? -DinteractiveMode=false ^
  More? -DgroupId=jp.example ^
  More? -DartifactId=pbstudy-slack-webservice ^
  More? -Dpackage=jp.example ^
  More? -DarchetypeVersion=2.29
  [INFO] Scanning for projects...
  [INFO]
  [INFO] ------------------< org.apache.maven:standalone-pom >-------------------
  [INFO] Building Maven Stub Project (No POM) 1
  [INFO] --------------------------------[ pom ]---------------------------------
  [INFO]
  [INFO] >>> maven-archetype-plugin:3.0.1:generate (default-cli) > generate-sources @ standalone-pom >>>
  [INFO]
  [INFO] <<< maven-archetype-plugin:3.0.1:generate (default-cli) < generate-sources @ standalone-pom <<<
  [INFO]
  [INFO]
  [INFO] --- maven-archetype-plugin:3.0.1:generate (default-cli) @ standalone-pom ---
  [INFO] Generating project in Batch mode
  [INFO] Archetype repository not defined. Using the one from [org.glassfish.jersey.archetypes:jersey-heroku-webapp:2.29] found in catalog remote
  Downloading from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/jersey-heroku-webapp/2.29/jersey-heroku-webapp-2.29.pom
  Downloaded from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/jersey-heroku-webapp/2.29/jersey-heroku-webapp-2.29.pom (2.2 kB at 5.1 kB/s)
  Downloading from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/project/2.29/project-2.29.pom
  Downloaded from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/project/2.29/project-2.29.pom (2.2 kB at 6.7 kB/s)
  Downloading from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/project/2.29/project-2.29.pom
  Downloaded from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/project/2.29/project-2.29.pom (96 kB at 98 kB/s)
  Downloading from central: https://repo.maven.apache.org/maven2/org/eclipse/ee4j/project/1.0.5/project-1.0.5.pom
  Downloaded from central: https://repo.maven.apache.org/maven2/org/eclipse/ee4j/project/1.0.5/project-1.0.5.pom (13 kB at 47 kB/s)
  Downloading from central: https://repo.maven.apache.org/maven2/org/glassfish/hk2/hk2-bom/2.5.0/hk2-bom-2.5.0.pom
  Downloaded from central: https://repo.maven.apache.org/maven2/org/glassfish/hk2/hk2-bom/2.5.0/hk2-bom-2.5.0.pom (7.7 kB at 28 kB/s)
  Downloading from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/jersey-heroku-webapp/2.29/jersey-heroku-webapp-2.29.jar
  Downloaded from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/jersey-heroku-webapp/2.29/jersey-heroku-webapp-2.29.jar (19 kB at 42 kB/s)
  [INFO] ----------------------------------------------------------------------------
  [INFO] Using following parameters for creating project from Archetype: jersey-heroku-webapp:2.29
  [INFO] ----------------------------------------------------------------------------
  [INFO] Parameter: groupId, Value: jp.example
  [INFO] Parameter: artifactId, Value: pbstudy-slack-webservice
  [INFO] Parameter: version, Value: 1.0-SNAPSHOT
  [INFO] Parameter: package, Value: jp.example
  [INFO] Parameter: packageInPathFormat, Value: jp/example
  [INFO] Parameter: package, Value: jp.example
  [INFO] Parameter: groupId, Value: jp.example
  [INFO] Parameter: artifactId, Value: pbstudy-slack-webservice
  [INFO] Parameter: version, Value: 1.0-SNAPSHOT
  [INFO] Project created from Archetype in dir: C:\Users\teruk\Documents\pbstudy-slack-webservice
  [INFO] ------------------------------------------------------------------------
  [INFO] BUILD SUCCESS
  [INFO] ------------------------------------------------------------------------
  [INFO] Total time:  22.869 s
  [INFO] Finished at: 2019-08-17T13:19:34+09:00
  [INFO] ------------------------------------------------------------------------
  ```

  

- [ ] Webサービスを公開する - heroku

- [ ] Slackアプリを作成する - OAuthのredirect先にherokuでのslack認証のpathを指定

- [ ] Webサービス開発 - redirectを受けたらアクセストークンをメモリ保存＆表示するコードを書く

- [ ] Slack認証のURLをブラウザで投げる→Webサービスがアクセストークンをメモリ保存＆表示する

- [ ] Webサービス開発 - アクセストークン受信時、Slackにメッセージを投げる

- [ ] SlackのEventyAPIを使ってみる

