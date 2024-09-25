### Ver branch remotos y checkout
- Ver remotos
> git branch -r
- Checkout del develop del remoto
> git checkout --track origin/develop

### En caso de que tenga commit para pull y commit para push
- Verifica antes que no sea el mismo archivo
> git pull origin develop --rebase

### Cambiar master to main
> git branch -m master main

### cambiar remote
- Listar remotos actuales
> git remote -v
- Cambiar a nueva url remota
> git remote set-url origin https://gitlab.com/proyecto/proyecto-backend.git  
- Volver a listar para verificar
> git remote -v





