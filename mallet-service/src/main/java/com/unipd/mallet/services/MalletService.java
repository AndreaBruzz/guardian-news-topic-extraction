package com.unipd.mallet.services;

import com.unipd.mallet.models.Topic;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.iterator.StringArrayIterator;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceLowercase;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Alphabet;
import cc.mallet.types.IDSorter;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

@Service
public class MalletService {

    public List<Topic> getTopics(List<String> articles, int numTopics, int numWords) throws Exception {
        // Creazione della lista di pipe per il preprocessing dei documenti
        ArrayList<Pipe> pipeList = new ArrayList<>();

        pipeList.add(new CharSequence2TokenSequence());
        pipeList.add(new TokenSequenceLowercase());
        pipeList.add(new TokenSequenceRemoveStopwords());
        pipeList.add(new TokenSequence2FeatureSequence());

        InstanceList instances = new InstanceList(new SerialPipes(pipeList));
        String[] articlesArray = articles.toArray(new String[0]);
        instances.addThruPipe(new StringArrayIterator(articlesArray));

        ParallelTopicModel model = new ParallelTopicModel(numTopics);
        model.addInstances(instances);

        model.setNumThreads(8);
        model.setNumIterations(1000);

        System.out.println("Running the model");

        // Run the model
        try {
            model.estimate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        System.out.println("Execution finished");

        Alphabet dataAlphabet = instances.getDataAlphabet();
        ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();
        ArrayList<Topic> topics = new ArrayList<>();

        for (int topic = 0; topic < numTopics; topic++) {
            Formatter out = new Formatter(new StringBuilder(), Locale.US);
            out.format("Topic %d:\n", topic);
            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();

            ArrayList<String> currentTopic = new ArrayList<>();
            int rank = 0;
            while (iterator.hasNext() && rank < numWords) {
                IDSorter idCountPair = iterator.next();
                out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
                currentTopic.add((String) dataAlphabet.lookupObject(idCountPair.getID()));
                rank++;
            }
            topics.add(new Topic(currentTopic));
            System.out.println(out);
        }
        return topics;
    }
}