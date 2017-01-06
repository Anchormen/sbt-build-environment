# Use build environments in SBT

## Description
This is an easy to use plugin which adds build environments to new and existing SBT projects. It requires SBT 0.13.5+

Build environments enable different resource configurations to be written and maintained, such as database settings or
user visible strings. These are then used automagically depending on the active environment.

## Usage
Add the following line to your `project/plugins.sbt`
```
addSbtPlugin("nl.anchormen.sbt" %% "sbt-build-environments" % "0.1.4")
```

Two build environments are defined, `dev` and `prod`. These define the `development` and `production` build environments,
respectively. Their resources files should be located in the `src` directory, at `src/dev/resources`
and `src/prod/resources`. Any resources should be added to both these directories. Each environment can be used to
compile, run and package the project. For example, to run a development build use the command `dev:run`. To create a
package for the productions environment, use the command `prod:package`