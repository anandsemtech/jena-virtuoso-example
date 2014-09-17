Maven repository for Virtuoso binaries
======================================

Add the repository `https://raw.githubusercontent.com/kops/jena-virtuoso-example/maven/repository` to your pom:

    <repositories>
      <repository>
        <id>virtuoso-github</id>
        <url>https://raw.githubusercontent.com/kops/jena-virtuoso-example/maven/repository</url>
        <releases>
          <enabled>true</enabled>
        </releases>
        <snapshots>
          <enabled>false</enabled>
        </snapshots>
      </repository>
    </repositories>

Then you can include these Artifacts:

    <dependency>
      <groupId>com.openlink.virtuoso</groupId>
      <artifactId>virtjdbc4-1</artifactId>
      <version>3.72</version>
    </dependency>

    <dependency>
      <groupId>com.openlink.virtuoso</groupId>
      <artifactId>virt_jena2</artifactId>
      <version>1.10</version>
    </dependency>
