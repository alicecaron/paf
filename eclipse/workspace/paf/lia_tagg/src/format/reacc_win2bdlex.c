/* TRANSF ACCENT -> BDLEX  */ 

#include <stdio.h>

int PossibleChangement(ch)
 char *ch;
{
/*  Cas des accents  */
if (((ch[0]=='a')||(ch[0]=='A')||
     (ch[0]=='e')||(ch[0]=='E')||
     (ch[0]=='i')||(ch[0]=='I')||
     (ch[0]=='o')||(ch[0]=='O')||
     (ch[0]=='u')||(ch[0]=='U'))&&
    ((ch[1]=='1')||(ch[1]=='2')||
     (ch[1]=='3')||(ch[1]=='4'))) return 1;
/*  Le c cedille  */
if (((ch[0]=='c')||(ch[0]=='C'))&&(ch[1]=='5')) return 1;
/*  Les formes \chiffre  */
if ((ch[0]=='\\')&&(ch[1]>='1')&&(ch[1]<='5')) return 1;
/*  Les formes \\  */
if ((ch[0]=='\\')&&(ch[1]=='\\')) return 1;
return 0;
}

void Accent2Bdlex(chaine)
 char *chaine;
{
char ch[90000];
int n,i;

for(n=i=0;chaine[i];i++)
 {
 switch (chaine[i])
  {
    case '�' :
       ch[n] = 'e'; ch[n+1] = '1';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'E'; ch[n+1] = '1';
       n+=2;
       break; 
    case '�' : 
       ch[n] = 'e'; ch[n+1] = '2';
       n+=2;
       break; 
    case '�' : 
       ch[n] = 'E'; ch[n+1] = '2';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'e'; ch[n+1] = '3';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'E'; ch[n+1] = '3';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'e'; ch[n+1] = '4';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'E'; ch[n+1] = '4';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'a'; ch[n+1] = '2';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'A'; ch[n+1] = '2';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'a'; ch[n+1] = '3';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'A'; ch[n+1] = '3';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'a'; ch[n+1] = '4';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'A'; ch[n+1] = '4';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'i'; ch[n+1] = '3';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'I'; ch[n+1] = '3';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'i'; ch[n+1] = '4';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'I'; ch[n+1] = '4';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'o'; ch[n+1] = '3';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'O'; ch[n+1] = '3';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'o'; ch[n+1] = '4';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'O'; ch[n+1] = '4';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'u'; ch[n+1] = '2';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'U'; ch[n+1] = '2';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'u'; ch[n+1] = '3';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'U'; ch[n+1] = '3';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'u'; ch[n+1] = '4';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'U'; ch[n+1] = '4';
       n+=2;
       break; 
    case '�' :
       ch[n] = 'c'; ch[n+1] = '5';
       n+=2;
       break;
    case '�' :
       ch[n] = 'C'; ch[n+1] = '5';
       n+=2;
       break;
    case '�' :
       ch[n] = '�';
       n++;
       break;
    case '�' :
       ch[n] = '�';
       n++;
       break;
    default :
       ch[n++] = chaine[i];
       if (PossibleChangement(chaine+i)) ch[n++]='\\';
       break;
  }
 }
ch[n]='\0';
strcpy(chaine,ch);
}

int main()
{
char ch[80000];
while(fgets(ch,80000,stdin))
 {
 Accent2Bdlex(ch);
 printf("%s",ch);
 }

exit(0);
}
 
