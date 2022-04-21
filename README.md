# WGFinance: Better together


Der Neubezug in eine WG ist meistens sehr stressig. Ein*e Mitbewohner*in geht und ein*e Neue*r kommt hinzu. Dabei ist es keine Seltenheit, dass Möbelstücke, die aus der WG-Kasse gezahlt wurden, von dem/der neuen Mitbewohner*in übernommen werden und Zahlungen geleistet werden sollen. Meistens wird einfach eine Summe grob überschlagen und ein Betrag eingefordert, für den niemand einen Beleg zu haben scheint. Oft handelt es sich aber nicht nur um ein Möbelstück, sondern um eine ganze Reihe von Dingen, die gemeinsam bezahlt wurden, nun in der WG bleiben sollen, aber eine Person entschädigt werden möchte. Besonders in Wohngemeinschaften, in denen es zu ständigem Wechsel der Mitbewohner*innen kommt, entstehen dadurch finanzielle Ungleichgewichte. Falsch notierte Rechnungen von über einem Jahr, geliehenes Geld oder eine Einkaufsbeteiligung, die vergessen wurde aufzulösen, sind Probleme, die durchaus in den meisten WGs auf die Mitbewohner*innen zu kommen. Die App WGFinance löst dieses Problem durch einen ganzheitlichen, in den WG-Alltag integrierbaren und vollständig digitalen Ansatz.

Nutzer*innen können für einen gemeinsamen Einkauf eine Liste anlegen. Nachdem die Zahlungen, die geleistet, in die Anwendung eingetragen und auf beliebig viele Mitbewohner*innen aufgeteilt wurden, werden diese in einer Übersicht angezeigt.
Durch die einfache Übersicht werden geleistete Zahlungen miteinander verrechnet. Die geleisteten Zahlungen bleiben aber in archivierten Übersicht erhalten. 

<br/>

## Development Setup

1. Make sure, that Java (>= version 11) is installed and set up correctly (check with javac -version command)
  - If another version was installed previously and the environment variable is corrected, you may need to restart
  - https://www.oracle.com/java/technologies/downloads/#jdk17-windows
2. Download the Git-Hub repository - or the existing APK - and run the application.

<br/>

##Git Hooks

On every commit git automatically runs checkstyle before committing.

<br/>

## CI/CD Pipeline

On every push and merge the pipeline runs checkstyle, all tests and checks if the code builds.
If a version tag (e.g. v1) was added, it will be released on the github project.

<br/>

## Branching

- No pushes direct to main if not approved
- Split into two groups working on two branches (Interface for UI, Development for MVP). These are regularly merged on backup.
- Quality gates, that need to be succeeded before a merge to backup is possible:
 - Reviewed by 1 peer within the group
 - Succeeded the build pipeline (includes style check, tests and possibly other gates)
 - Test of the merge from at least two peers.

<br/>

## Development Workflow

**Regularly check for PRs that need to be reviewed.**

1. Communication on who does what with weekly meeting to talk about progress, issues and extra meeting to present the progress
2. Issues were closed by someone taking care of them. The takeover was based on who was already working on this sub-area.
3. Work on your branch
4. Progress was merged after everyone was done to the backup branch.
5. Merge conflicts were resolved.

<br/>

## Contributors

The individual team members did not have fixed areas of responsibility. Everybody worked in many areas, yet thematic focuses have emerged over time.
To learn more about the division of tasks and the schedule follow this link: https://tinyurl.com/y25f6kdn 
