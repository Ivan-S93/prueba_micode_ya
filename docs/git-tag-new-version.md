

## Version: 0.0.1
- para esto debe estar iniciado git flow
> git flow init


### EN PROYECTO SPRING BOOT
- Cambiar a v0.0.1<version> en pom.xml, el que esta debajo de <artifactId>artifactIdReplace</artifactId> 
- [pom.xml](../pom.xml)


### EN PROYECTO REACT
- Mantener la misma version que springboot
- Seguir instructivo de git-tag-new-version.md del proyecto react


### Commit
- Ver cambios realizados 
- Commitear cambios

- El main debe estar pulleado
> git checkout main
> git pull origin main
> git checkout develop

- MAC TEU
> git checkout main && git pull origin main && git checkout develop
- WIN TEU
> git checkout main ; git pull origin main ; git checkout develop


### Versionar en git

Windows
> git flow release start v0.0.1; git flow release finish -m 'v0.0.1-liberada' 'v0.0.1' ; git push origin --all ; git push origin --tags

Mac
> git flow release start v0.0.1 && git flow release finish -m 'v0.0.1-liberada' 'v0.0.1' && git push origin --all && git push origin --tags

- Checkout a develop




