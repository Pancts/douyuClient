dim path,allfile
set wshshell=createobject("wscript.shell")
path=wshshell.currentdirectory
set objfso=createobject("scripting.filesystemobject")
set objfolders=objfso.getfolder(path).subfolders
for each objfolder in objfolders
     path=objfolder:allfile=""
     set objfiles=objfso.getfolder(path).files
     for each objfile in objfiles
          allfile=allfile&vbcrlf&objfile.name
     next
     wshshell.popup allfile,3
next
set objfiles=nothing
set objfolders=nothing
set objfso=nothing
set wshshell=nothing