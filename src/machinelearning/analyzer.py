from nltk.corpus import stopwords
from nltk.tokenize import RegexpTokenizer
from nltk.stem import SnowballStemmer, PorterStemmer
from nltk.corpus import wordnet


if __name__=='__main__':
    
    tokenizer = RegexpTokenizer("[\w']+")
    tokens=tokenizer.tokenize("Today's weather is so good. Better than ever. I am so good today");
    print tokens
    english_stops=set(stopwords.words('english'));
    abc=[word.lower() for word in tokens if word not in english_stops]
    print abc
    stemmer = SnowballStemmer('english')
    result=[ stemmer.stem(word) for word in abc]
    print result
    dic={}
    for word in result:
        if word in dic.keys():
            dic[word]=dic[word]+1
        else:
            dic[word]=1
    print dic
    adjs=['pretty','beautiful','sexy']
    out=[]
   


    while adjs:
        word=adjs[0]
        out.append(word)
        syns=wordnet.synsets(word)
        print "now process %s" %word
        for syn in syns:
            for lemma in syn.lemmas:
                if not lemma.name in out:
                    out.append(lemma.name)
         #   pos=syn.name.find('.')
          #  tmp=syn.name[0:pos]
           # if not tmp in out:
            #    adjs.append(tmp)
        adjs.pop(0) 
    print out
