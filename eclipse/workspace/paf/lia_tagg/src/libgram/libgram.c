/*
#    --------------------------------------------------------
#    LIA_TAGG: a statistical POS tagger + syntactic bracketer
#    --------------------------------------------------------
#
#    Copyright (C) 2001 FREDERIC BECHET
#
#    ..................................................................
#
#    This file is part of LIA_TAGG
#
#    LIA_TAGG is free software; you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation; either version 2 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program; if not, write to the Free Software
#    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
#    ..................................................................
#
#    Contact :
#              FREDERIC BECHET - LIA - UNIVERSITE D'AVIGNON
#              AGROPARC BP1228 84911  AVIGNON  CEDEX 09  FRANCE
#              frederic.bechet@lia.univ-avignon.fr
#    ..................................................................
*/
/*  Usefule things for the 'gram' module  */
/*  FRED 0498 - Modif multi ML - 0399  */

/*................................................................*/

#include <libgram.h>
#include <string.h>
/*................................................................*/

/*  Declaration de la table de ML  */

ty_ml Table_ML[NB_MAX_ML];

/*  Nombre de ML present en memoire  */

int NB_ML=0;

/*................................................................*/

/* Allocation d'un nouveau ML */

ty_ml cons_ml(long nb1,long nb2,long nb3,int si_hash,int si_2g,int si_log10)
{
ty_ml pt;
pt=(ty_ml)malloc(sizeof(struct type_ml));
pt->pmc=NULL;
pt->lexique=NULL;
pt->NB1GRAM=nb1;
pt->NB2GRAM=nb2;
pt->NB3GRAM=nb3;
pt->GRAM_SI_HASH=si_hash;
pt->GRAM_SI_2G=si_2g;
pt->GRAM_LOG10=si_log10;
pt->TABL1GRAM=(type_1gram*)malloc(sizeof(type_1gram)*(pt->NB1GRAM+MARGE));
if (pt->TABL1GRAM==NULL) return NULL;
pt->TABL2GRAM=(type_2gram*)malloc(sizeof(type_2gram)*(pt->NB2GRAM+MARGE));
if (pt->TABL2GRAM==NULL) return NULL;
if (si_2g==0)
 {
 pt->TABL3GRAM=(type_3gram*)malloc(sizeof(type_3gram)*(pt->NB3GRAM+MARGE));
 if (pt->TABL3GRAM==NULL) return NULL;
 }
else pt->TABL3GRAM=NULL;
return pt;
}

void delete_ml(ty_ml pt_ml)
{
if (pt_ml)
 {
 if (pt_ml->lexique) delete_lexique(pt_ml->lexique);
 if (pt_ml->pmc) delete_pmc(pt_ml->pmc);
 if (pt_ml->TABL1GRAM) free(pt_ml->TABL1GRAM);
 if (pt_ml->TABL2GRAM) free(pt_ml->TABL2GRAM);
 if (pt_ml->TABL3GRAM) free(pt_ml->TABL3GRAM);
 free(pt_ml);
 }
}

/*................................................................*/

/*  Egalite  */

int H1compar(const void *a ,const void *b )
{
int n;
type_1gram *i,*j;
i=(type_1gram *)a;
j=(type_1gram *)b;
for(n=Nb1Byte-1;n>=0;n--)
 {
 if (i->cle[n]>j->cle[n]) return 1;
 if (i->cle[n]<j->cle[n]) return -1;
 }
return 0;
}

int H2compar(const void *a ,const void *b )
{
int n;
type_2gram *i,*j;
i=(type_2gram *)a;
j=(type_2gram *)b;
for(n=Nb2Byte-1;n>=0;n--)
 {
 if (i->cle[n]>j->cle[n]) return 1;
 if (i->cle[n]<j->cle[n]) return -1;
 }
return 0;
}

int H3compar(const void *a ,const void *b )
{
int n;
type_3gram *i,*j;
i=(type_3gram *)a;
j=(type_3gram *)b;
for(n=Nb3Byte-1;n>=0;n--)
 {
 if (i->cle[n]>j->cle[n]) return 1;
 if (i->cle[n]<j->cle[n]) return -1;
 }
return 0;
}

int SiEgal1(wrd_index_t i_mot ,long indice ,ty_ml pt_ml)
{
return (pt_ml->TABL1GRAM[indice].cle[0]==BYTE1(i_mot)) &&
       (pt_ml->TABL1GRAM[indice].cle[1]==BYTE2(i_mot)) &&
       (pt_ml->TABL1GRAM[indice].cle[2]==BYTE3(i_mot))?1:0;
}

int SiEgal2(wrd_index_t i_mot1 ,wrd_index_t i_mot2 ,long indice ,ty_ml pt_ml)
{
return (pt_ml->TABL2GRAM[indice].cle[0]==BYTE1(i_mot1)) &&
       (pt_ml->TABL2GRAM[indice].cle[1]==BYTE2(i_mot1)) &&
       (pt_ml->TABL2GRAM[indice].cle[2]==BYTE3(i_mot1)) &&

       (pt_ml->TABL2GRAM[indice].cle[3]==BYTE1(i_mot2)) &&
       (pt_ml->TABL2GRAM[indice].cle[4]==BYTE2(i_mot2)) &&
       (pt_ml->TABL2GRAM[indice].cle[5]==BYTE3(i_mot2))?1:0;
}

int SiEgal3(wrd_index_t i_mot1 ,wrd_index_t i_mot2 ,wrd_index_t i_mot3 ,long indice ,ty_ml pt_ml)
{
return (pt_ml->TABL3GRAM[indice].cle[0]==BYTE1(i_mot1)) &&
       (pt_ml->TABL3GRAM[indice].cle[1]==BYTE2(i_mot1)) &&
       (pt_ml->TABL3GRAM[indice].cle[2]==BYTE3(i_mot1)) &&

       (pt_ml->TABL3GRAM[indice].cle[3]==BYTE1(i_mot2)) &&
       (pt_ml->TABL3GRAM[indice].cle[4]==BYTE2(i_mot2)) &&
       (pt_ml->TABL3GRAM[indice].cle[5]==BYTE3(i_mot2)) &&

       (pt_ml->TABL3GRAM[indice].cle[6]==BYTE1(i_mot3)) &&
       (pt_ml->TABL3GRAM[indice].cle[7]==BYTE2(i_mot3)) &&
       (pt_ml->TABL3GRAM[indice].cle[8]==BYTE3(i_mot3))?1:0;
}

/*................................................................*/

/*  Gestion des 1.2.3-gram au format arpa  */
/*  FRED 0398 - Modif multi ML - 0399  */

/*................................................................*/

/*  Lecture  */

err_t gram_module_init(char *chfich ,int *num_ml, int n_gram , const err_t trace)
{
FILE *file;
long nb1,nb2,nb3;
int si_hash,si_log10;
char ch[100];

if ((n_gram!=2)&&(n_gram!=3))
 {
 fprintf(stderr,"Sorry, you can load only 2GRAM or 3GRAM models !!\n");
 return SIR_RANGE_ERR;
 }

*num_ml=NB_ML++;

if (*num_ml>=NB_MAX_ML) return SIR_RANGE_ERR;

if (trace & SIR_GRAM_TRACE)
 fprintf(stderr,"Loading of the n-gram model : number %d\n",*num_ml);

sprintf(ch,"%s.desc",chfich);
if (!(file=fopen(ch,"r"))) return SIR_READ_OPEN_ERR;
fgets(ch,100,file);
if (sscanf(ch,"%ld %ld %ld",&nb1,&nb2,&nb3)!=3)
 {
 fprintf(stderr,"ERREUR: can't read the size of the 1,2,3 gram tables in %s\n",chfich);
 return SIR_RANGE_ERR;
 }
fgets(ch,100,file);
if (!strncmp(ch,"HASH",4)) si_hash=1; else si_hash=0;
fgets(ch,100,file);
if (!strncmp(ch,"LOG_10",6))
 {
 si_log10=1;
 if (trace & SIR_GRAM_TRACE) fprintf(stderr,"The logprob of the model are : LOG 10\n");
 }
else
 {
 si_log10=0;
 if (trace & SIR_GRAM_TRACE) fprintf(stderr,"The logprob of the model are : LOG e\n");
 }
fgets(ch,100,file);
if (!strncmp(ch,"TRIGRAMME",9))
 {
 if (n_gram==2)
  { if (trace & SIR_GRAM_TRACE) fprintf(stderr,"3GRAM model loaded as a 2GRAM\n"); }
 else
  { if (trace & SIR_GRAM_TRACE) fprintf(stderr,"3GRAM model\n"); }
 }
else
 {
 if (n_gram==3)
  {
  fprintf(stderr,"Sorry, you can't load a 2GRAM model as a 3GRAM model !!!!\n");
  return SIR_RANGE_ERR;
  }
 if (trace & SIR_GRAM_TRACE) fprintf(stderr,"2GRAM model\n");
 }
fclose(file);

Table_ML[*num_ml]=cons_ml(nb1,nb2,nb3,si_hash,n_gram==2?1:0,si_log10);

if (Table_ML[*num_ml]==NULL) return SIR_ALLOC_ERR;

sprintf(ch,"%s.1g",chfich);
if (!(file=fopen(ch,"rb"))) return SIR_READ_OPEN_ERR;
if (!(fread(Table_ML[*num_ml]->TABL1GRAM,sizeof(type_1gram)*(nb1+MARGE),1,file)))
 return SIR_READ_ERR;
fclose(file);

sprintf(ch,"%s.2g",chfich);
if (!(file=fopen(ch,"rb"))) return SIR_READ_OPEN_ERR;
if (!(fread(Table_ML[*num_ml]->TABL2GRAM,sizeof(type_2gram)*(nb2+MARGE),1,file)))
 return SIR_READ_ERR;
fclose(file);

if (n_gram==3)
 {
 sprintf(ch,"%s.3g",chfich);
 if (!(file=fopen(ch,"rb"))) return SIR_READ_OPEN_ERR;
 if (!(fread(Table_ML[*num_ml]->TABL3GRAM,sizeof(type_3gram)*(nb3+MARGE),1,file)))
  return SIR_READ_ERR;
 fclose(file);
 }
return(CORRECT);
}

err_t gram_module_reset(const int num_ml,const err_t trace)
{
if (trace & SIR_GRAM_TRACE)
 fprintf(stderr,"Deletion of the n-gram model : number %d\n",num_ml);
if ((num_ml>NB_ML)||(Table_ML[num_ml]==NULL)) return SIR_FATAL_ERR;

free(Table_ML[num_ml]->TABL1GRAM);
free(Table_ML[num_ml]->TABL2GRAM);
if (Table_ML[num_ml]->GRAM_SI_2G==0) free(Table_ML[num_ml]->TABL3GRAM);

if (Table_ML[num_ml]->lexique) delete_lexique(Table_ML[num_ml]->lexique);

return(CORRECT);
}

/*................................................................*/

/*  Recherche des NGrams par HashCode  */

int HashRecherche1Gram(wrd_index_t i_mot ,flogprob_t *lp ,flogprob_t *fr ,ty_ml pt_ml)
{
wrd_index_t essai,indice,valh1,valh2;
indice=valh1=H1Value(i_mot,pt_ml);
valh2=Double1H(i_mot,pt_ml);
for (essai=1;(essai<=pt_ml->NB1GRAM)&&(Case1Vide(indice,pt_ml)==0)&&
	(!SiEgal1(i_mot,indice,pt_ml));essai++)
 indice=EssaiSuivant(valh1,valh2,essai,pt_ml->NB1GRAM);
if ((essai<=pt_ml->NB1GRAM)&&(Case1Vide(indice,pt_ml)==0))
 {
 *lp=pt_ml->TABL1GRAM[indice].lp;
 *fr=pt_ml->TABL1GRAM[indice].fr;
 return 1;
 }
return 0;
}

int HashRecherche2Gram(wrd_index_t i_mot1 ,wrd_index_t i_mot2 ,flogprob_t *lp ,flogprob_t *fr ,ty_ml pt_ml)
{
wrd_index_t essai,indice,valh1,valh2;
indice=valh1=H2Value(i_mot1,i_mot2,pt_ml);
valh2=Double2H(i_mot1,i_mot2,pt_ml);
for (essai=1;(essai<=pt_ml->NB2GRAM)&&(Case2Vide(indice,pt_ml)==0)&&
	(!SiEgal2(i_mot1,i_mot2,indice,pt_ml));essai++)
 indice=EssaiSuivant(valh1,valh2,essai,pt_ml->NB2GRAM);
if ((essai<=pt_ml->NB2GRAM)&&(Case2Vide(indice,pt_ml)==0))
 {
 *lp=pt_ml->TABL2GRAM[indice].lp;
 *fr=pt_ml->TABL2GRAM[indice].fr;
 return 1;
 }
return 0;
}

int HashRecherche3Gram(wrd_index_t i_mot1 ,wrd_index_t i_mot2 ,wrd_index_t i_mot3 ,flogprob_t *lp ,ty_ml pt_ml)
{
wrd_index_t essai,indice,valh1,valh2;
indice=valh1=H3Value(i_mot1,i_mot2,i_mot3,pt_ml);
valh2=Double3H(i_mot1,i_mot2,i_mot3,pt_ml);
for (essai=1;(essai<=pt_ml->NB3GRAM)&&(Case3Vide(indice,pt_ml)==0)&&
	(!SiEgal3(i_mot1,i_mot2,i_mot3,indice,pt_ml));essai++)
 indice=EssaiSuivant(valh1,valh2,essai,pt_ml->NB3GRAM);
if ((essai<=pt_ml->NB3GRAM)&&(Case3Vide(indice,pt_ml)==0))
 {
 *lp=pt_ml->TABL3GRAM[indice].lp;
 return 1;
 }
return 0;
}

/*................................................................*/

/*  Recherche des NGrams par dichotomie  */

int DichoRecherche1Gram(wrd_index_t i_mot , flogprob_t *lp ,flogprob_t *fr ,ty_ml pt_ml)
{
type_1gram *resu,key;
key.cle[0]=BYTE1(i_mot); key.cle[1]=BYTE2(i_mot); key.cle[2]=BYTE3(i_mot);
resu=(type_1gram*)bsearch((void*)(&key),pt_ml->TABL1GRAM,pt_ml->NB1GRAM+MARGE,
	sizeof(type_1gram),H1compar);
if (resu==NULL) return 0;
*lp=resu->lp; *fr=resu->fr;
return 1;
}

int DichoRecherche2Gram(wrd_index_t i_mot1 ,wrd_index_t i_mot2 ,flogprob_t *lp ,flogprob_t *fr ,ty_ml pt_ml)
{
type_2gram *resu,key;
key.cle[0]=BYTE1(i_mot1); key.cle[1]=BYTE2(i_mot1); key.cle[2]=BYTE3(i_mot1);
key.cle[3]=BYTE1(i_mot2); key.cle[4]=BYTE2(i_mot2); key.cle[5]=BYTE3(i_mot2);
resu=(type_2gram*)bsearch((void*)(&key),pt_ml->TABL2GRAM,
	pt_ml->NB2GRAM+MARGE,sizeof(type_2gram),H2compar);
if (resu==NULL) return 0;
*lp=resu->lp; *fr=resu->fr;
return 1;
}

int DichoRecherche3Gram(wrd_index_t i_mot1 ,wrd_index_t i_mot2 ,wrd_index_t i_mot3 ,flogprob_t *lp ,ty_ml pt_ml)
{
type_3gram *resu,key;
key.cle[0]=BYTE1(i_mot1); key.cle[1]=BYTE2(i_mot1); key.cle[2]=BYTE3(i_mot1);
key.cle[3]=BYTE1(i_mot2); key.cle[4]=BYTE2(i_mot2); key.cle[5]=BYTE3(i_mot2);
key.cle[6]=BYTE1(i_mot3); key.cle[7]=BYTE2(i_mot3); key.cle[8]=BYTE3(i_mot3);
resu=(type_3gram*)bsearch((void*)(&key),pt_ml->TABL3GRAM,
	pt_ml->NB3GRAM+MARGE,sizeof(type_3gram),H3compar);
if (resu==NULL) return 0;
*lp=resu->lp;
return 1;
}

/*................................................................*/

/*  Fonctions de recherche generiques  */

int Recherche1Gram(wrd_index_t i_mot , flogprob_t *lp ,flogprob_t *fr ,ty_ml pt_ml)
{
if (pt_ml->GRAM_SI_HASH)
 return HashRecherche1Gram(i_mot,lp,fr,pt_ml);
else
 return DichoRecherche1Gram(i_mot,lp,fr,pt_ml);
}

int Recherche2Gram(wrd_index_t i_mot1 ,wrd_index_t i_mot2 ,flogprob_t *lp ,flogprob_t *fr ,ty_ml pt_ml)
{
if (pt_ml->GRAM_SI_HASH)
 return HashRecherche2Gram(i_mot1,i_mot2,lp,fr,pt_ml);
else
 return DichoRecherche2Gram(i_mot1,i_mot2,lp,fr,pt_ml);
}

int Recherche3Gram(wrd_index_t i_mot1 ,wrd_index_t i_mot2 ,wrd_index_t i_mot3 ,flogprob_t *lp ,ty_ml pt_ml)
{
if (pt_ml->GRAM_SI_HASH)
 return HashRecherche3Gram(i_mot1,i_mot2,i_mot3,lp,pt_ml);
else
 return DichoRecherche3Gram(i_mot1,i_mot2,i_mot3,lp,pt_ml);
}

/*................................................................*/

/*  Fonction renvoyant la proba d'un 3-gram selon la formule :

p(wd3|wd1,wd2)= if(trigram exists)           p_3(wd1,wd2,wd3)
		else if(bigram w1,w2 exists) bo_wt_2(w1,w2)*p(wd3|wd2)
		else                         p(wd3|w2)

p(wd2|wd1)= if(bigram exists) p_2(wd1,wd2)
	    else              bo_wt_1(wd1)*p_1(wd2)
*/

err_t gram_proba_to_bigram(logprob_t *proba,const wrd_index_t wrd1,
			 const wrd_index_t wrd2,const int num_ml, const err_t trace)
{
flogprob_t lp,fr,lp1,fr1,lp2,fr2;

if ((num_ml>NB_ML)||(Table_ML[num_ml]==NULL))
 {
 fprintf(stderr,"Le ML numero:%d n'existe pas .... NB_ML=%d\n",num_ml,NB_ML);
 exit(0);
 }

if (trace & SIR_GRAM_TRACE)
 fprintf(stderr,"GRAM : P(%d,%d)\n",wrd1,wrd2);

if (Recherche2Gram(wrd1,wrd2,&lp,&fr,Table_ML[num_ml]))
 {
 if (trace & SIR_GRAM_TRACE)
  {
  fprintf(stderr,"J'ai trouve le bigramme : %ld %ld\n",wrd1,wrd2);
  fprintf(stderr,"  avec : lp=%f et fr=%f\n",lp,fr);
  }
 *proba=(logprob_t)lp;
 return CORRECT;
 }
else
 {
 if (trace & SIR_GRAM_TRACE)
  fprintf(stderr,"Je n'ai pas trouve le bigramme : %ld %ld\n",wrd1,wrd2);
 if (!Recherche1Gram(wrd1,&lp1,&fr1,Table_ML[num_ml]))
  {
  if (trace & SIR_GRAM_TRACE) fprintf(stderr,"(hash) je ne trouve pas : %d\n",wrd1);
  /*return GRAM_CODE_UNKNOWN;*/
  /* MODIF : SI l'UNIGRAM EST ABSENT ON RENVOIT LE MOT INCONNU */
  if (!Recherche1Gram(0,&lp1,&fr1,Table_ML[num_ml])) return GRAM_CODE_UNKNOWN;
  }
 if (!Recherche1Gram(wrd2,&lp2,&fr2,Table_ML[num_ml]))
  {
  if (trace & SIR_GRAM_TRACE) fprintf(stderr,"(hash) je ne trouve pas : %d\n",wrd2);
  /*return GRAM_CODE_UNKNOWN;*/
  /* MODIF : SI l'UNIGRAM EST ABSENT ON RENVOIT LE MOT INCONNU */
  if (!Recherche1Gram(0,&lp1,&fr1,Table_ML[num_ml])) return GRAM_CODE_UNKNOWN;
  }
 if (trace & SIR_GRAM_TRACE)
  fprintf(stderr,"Backoff sur l'unigramme avec : P(%ld)=%f et FR(%ld)=%f d'ou lp=%f\n",
	wrd2,lp2,wrd1,fr1,fr1+lp2);
 *proba=(logprob_t)(fr1+lp2);
 return CORRECT;
 }
}

err_t gram_proba_to_trigram(logprob_t *proba,const wrd_index_t wrd1,const wrd_index_t wrd2,
	const wrd_index_t wrd3,const int num_ml,const err_t trace)
{
flogprob_t lp,lp1,fr1;
logprob_t lp2;
err_t reto;

if ((num_ml>NB_ML)||(Table_ML[num_ml]==NULL))
 {
 fprintf(stderr,"Le ML numero:%d n'existe pas .... NB_ML=%d\n",num_ml,NB_ML);
 exit(0);
 }

if (Table_ML[num_ml]->GRAM_SI_2G==1) return SIR_FATAL_ERR;

if (trace & SIR_GRAM_TRACE)
 fprintf(stderr,"GRAM : P(%d,%d,%d)\n",wrd1,wrd2,wrd3);

if (Recherche3Gram(wrd1,wrd2,wrd3,&lp,Table_ML[num_ml]))
 { *proba=(logprob_t)lp; return CORRECT; }
else
 {
 if (!Recherche2Gram(wrd1,wrd2,&lp1,&fr1,Table_ML[num_ml]))
  {
  if (trace & SIR_GRAM_TRACE)
   fprintf(stderr,"(hash) je ne trouve pas : %d %d\n",wrd1,wrd2);
  fr1=0;
  }
 reto=gram_proba_to_bigram(&lp2,wrd2,wrd3,num_ml,trace);
 if (reto!=CORRECT) return reto;
 *proba=(logprob_t)fr1+lp2;
 return CORRECT;
 }
}

/*................................................................*/
  
