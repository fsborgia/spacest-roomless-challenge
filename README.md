# Istruzioni challenge

Creazione di un set di API per la gestione di una raccolta di segnalibri (simile a Pocket oppure Raindrop.io):

    Implementazione di operazioni CRUD per i segnalibri;
    Implementazione di un'ulteriore operazione di search, con paging;
    Interazione con il database ed eventualmente con il container fornito;
    Valutare meccanismi di autenticazione (l'implementazione e' un bonus);
    Utilizzo di un version control (preferibilmente Git) durante lo sviluppo;

In allegato trovi lo schema da utilizzare come riferimento e il Dockerfile.

Il nostro stack tecnologico e' cosi formato:

    TypeScript
    npm
    NestJS
    Prisma (ORM)
    MySQL
    AWS (Lambda, EC2, RDS, ...)
    GitHub Actions (CI/CD)
    Serverless.
