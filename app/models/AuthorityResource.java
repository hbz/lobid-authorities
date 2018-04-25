package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;
import org.elasticsearch.common.geo.GeoPoint;

import controllers.HomeController;
import models.EntityFacts.Link;

public class AuthorityResource {

	private static final int SHORTEN = 10;

	public final static String DNB_PREFIX = "http://d-nb.info/gnd/";

	private String id;
	private List<String> type;

	public List<String> definition;
	public List<String> biographicalOrHistoricalInformation;
	public List<Map<String, Object>> hasGeometry;
	public String gndIdentifier;
	public String preferredName;
	public List<String> variantName;
	public List<Map<String, Object>> sameAs;
	public List<Map<String, Object>> geographicAreaCode;
	public List<Map<String, Object>> gndSubjectCategory;
	public List<Map<String, Object>> relatedTerm;
	public List<Map<String, Object>> relatedPerson;
	public List<Map<String, Object>> relatedWork;
	public List<Map<String, Object>> broaderTermInstantial;
	public List<Map<String, Object>> broaderTermGeneral;
	public List<Map<String, Object>> broaderTermGeneric;
	public List<Map<String, Object>> broaderTermPartitive;
	public List<String> dateOfConferenceOrEvent;
	public List<Map<String, Object>> placeOfConferenceOrEvent;
	public List<Map<String, Object>> spatialAreaOfActivity;
	public List<String> dateOfEstablishment;
	public List<Map<String, Object>> placeOfBusiness;
	public List<Map<String, Object>> wikipedia;
	public List<Map<String, Object>> homepage;
	public List<Map<String, Object>> topic;
	public List<Map<String, Object>> gender;
	public List<Map<String, Object>> professionOrOccupation;
	public List<Map<String, Object>> precedingPlaceOrGeographicName;
	public List<Map<String, Object>> succeedingPlaceOrGeographicName;
	public List<String> dateOfTermination;
	public List<String> academicDegree;
	public List<Map<String, Object>> acquaintanceshipOrFriendship;
	public List<Map<String, Object>> familialRelationship;
	public List<Map<String, Object>> placeOfActivity;
	public List<String> dateOfBirth;
	public List<Map<String, Object>> placeOfBirth;
	public List<Map<String, Object>> placeOfDeath;
	public List<String> dateOfDeath;
	public List<Map<String, Object>> professionalRelationship;
	public List<Map<String, Object>> hierarchicalSuperiorOfTheCorporateBody;
	public List<Map<String, Object>> firstAuthor;
	public List<String> publication;
	public List<String> dateOfProduction;
	public List<Map<String, Object>> mediumOfPerformance;
	public List<Map<String, Object>> firstComposer;
	public List<String> dateOfPublication;
	public EntityFacts entityFacts;

	public List<String> creatorOf;

	public String getId() {
		return id.substring(DNB_PREFIX.length());
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getType() {
		return type.stream().filter(t -> !t.equals("AuthorityResource")).collect(Collectors.toList());
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AuthorityResource [id=" + id + "]";
	}

	public String title() {
		return preferredName;
	}

	public GeoPoint location() {
		if (hasGeometry == null)
			return null;
		@SuppressWarnings("unchecked")
		String geoString = ((List<Map<String, Object>>) hasGeometry.get(0).get("asWKT")).get(0).get("@value")
				.toString();
		List<Double> lonLat = scanGeoCoordinates(geoString);
		if (lonLat.size() != 2) {
			throw new IllegalArgumentException("Could not scan geo location from: " + geoString + ", got: " + lonLat);
		}
		return new GeoPoint(lonLat.get(1), lonLat.get(0));
	}

	private List<Double> scanGeoCoordinates(String geoString) {
		List<Double> lonLat = new ArrayList<Double>();
		try (@SuppressWarnings("resource") // it's the same scanner!
		Scanner s = new Scanner(geoString).useLocale(Locale.US)) {
			while (s.hasNext()) {
				if (s.hasNextDouble()) {
					lonLat.add(s.nextDouble());
				} else {
					s.next();
				}
			}
		}
		return lonLat;
	}

	public List<Pair<String, String>> generalFields() {
		List<Pair<String, String>> fields = new ArrayList<>();
		addValues("type", typeLinks(), fields);
		addValues("gndIdentifier", Arrays.asList(gndIdentifier), fields);
		addValues("definition", definition, fields);
		addValues("biographicalOrHistoricalInformation", biographicalOrHistoricalInformation, fields);
		addIds("firstAuthor", firstAuthor, fields);
		addIds("firstComposer", firstComposer, fields);
		addIds("mediumOfPerformance", mediumOfPerformance, fields);
		addIds("professionOrOccupation", professionOrOccupation, fields);
		addIds("homepage", homepage, fields);
		addValues("academicDegree", academicDegree, fields);
		addIds("geographicAreaCode", geographicAreaCode, fields);
		addIds("gndSubjectCategory", gndSubjectCategory, fields);
		addIds("topic", topic, fields);
		addIds("hierarchicalSuperiorOfTheCorporateBody", hierarchicalSuperiorOfTheCorporateBody, fields);
		addIds("broaderTermPartitive", broaderTermPartitive, fields);
		addIds("broaderTermInstantial", broaderTermInstantial, fields);
		addIds("broaderTermGeneral", broaderTermGeneral, fields);
		addIds("broaderTermGeneric", broaderTermGeneric, fields);
		addIds("relatedTerm", relatedTerm, fields);
		addValues("dateOfConferenceOrEvent", dateOfConferenceOrEvent, fields);
		addIds("placeOfConferenceOrEvent", placeOfConferenceOrEvent, fields);
		addIds("relatedPerson", relatedPerson, fields);
		addIds("professionalRelationship", professionalRelationship, fields);
		addIds("acquaintanceshipOrFriendship", acquaintanceshipOrFriendship, fields);
		addIds("familialRelationship", familialRelationship, fields);
		addIds("placeOfBusiness", placeOfBusiness, fields);
		addIds("placeOfActivity", placeOfActivity, fields);
		addIds("spatialAreaOfActivity", spatialAreaOfActivity, fields);
		addIds("precedingPlaceOrGeographicName", precedingPlaceOrGeographicName, fields);
		addIds("succeedingPlaceOrGeographicName", succeedingPlaceOrGeographicName, fields);
		addIds("gender", gender, fields);
		addValues("dateOfBirth", dateOfBirth, fields);
		addValues("dateOfDeath", dateOfDeath, fields);
		addIds("placeOfBirth", placeOfBirth, fields);
		addIds("placeOfDeath", placeOfDeath, fields);
		addValues("dateOfEstablishment", dateOfEstablishment, fields);
		addValues("dateOfTermination", dateOfTermination, fields);
		addValues("dateOfProduction", dateOfProduction, fields);
		addValues("dateOfPublication", dateOfPublication, fields);
		addValues("variantName", variantName, fields);
		addValues("creatorOf", creatorOf, fields);
		return fields;
	}

	private List<String> typeLinks() {
		List<String> subTypes = getType().stream()
				.filter(t -> HomeController.CONFIG.getObject("types").keySet().contains(t))
				.collect(Collectors.toList());
		List<String> typeLinks = (subTypes.isEmpty() ? getType() : subTypes).stream()
				.map(t -> String.format("<a href='%s'>%s</a>",
						controllers.routes.HomeController.search("", "+(type:" + t + ")", 0, 10, "").toString(),
						models.GndOntology.label(t)))
				.collect(Collectors.toList());
		return typeLinks;
	}

	public List<Pair<String, String>> additionalLinks() {
		ArrayList<Link> links = new ArrayList<>(new TreeSet<>(entityFacts.getLinks()));
		List<Pair<String, String>> result = new ArrayList<>();
		if (!links.isEmpty()) {
			String field = "sameAs";
			String value = IntStream.range(0, links.size()).mapToObj(i -> html(field, links, i))
					.collect(Collectors.joining(" | "));
			result.add(Pair.of(field, value));
		}
		return result;
	}

	private String html(String field, ArrayList<Link> links, int i) {
		Link link = links.get(i);
		String result = String.format("<a href='%s'><img src='%s' style='height:1em'/>&nbsp;%s</a>", //
				link.url, link.image, link.label);
		return withDefaultHidden(field, links.size(), i, result);
	}

	private void addIds(String field, List<Map<String, Object>> list, List<Pair<String, String>> result) {
		if (list != null) {
			addValues(field, list.stream().map(m -> m.get("id").toString()).collect(Collectors.toList()), result);
		}
	}

	private void addValues(String field, List<String> list, List<Pair<String, String>> result) {
		if (list != null && list.size() > 0) {
			String value = IntStream.range(0, list.size()).mapToObj(i -> process(field, list.get(i), i, list.size()))
					.collect(Collectors.joining(" | "));
			result.add(Pair.of(field, value));
		}
	}

	private String process(String field, String value, int i, int size) {
		String label = GndOntology.label(value);
		String result = label;
		if ("creatorOf".equals(field)) {
			result = String.format("<a href='%s'>%s</a>",
					controllers.routes.HomeController.authority(value.replace(DNB_PREFIX, ""), null), label);
		} else if (Arrays.asList("wikipedia", "sameAs", "depiction", "homepage").contains(field)) {
			result = String.format("<a href='%s'>%s</a>", value, value);
		} else if (value.startsWith("http")) {
			String link = value.startsWith(DNB_PREFIX)
					? controllers.routes.HomeController.authorityDotFormat(value.replace(DNB_PREFIX, ""), "html")
							.toString()
					: value;
			String search = controllers.routes.HomeController.search(field + ":\"" + value + "\"", "", 0, 10, "html")
					.toString();
			result = String.format(
					"<a id='%s-%s' title='Weitere Einträge mit %s \"%s\" suchen' href='%s'>%s</a>&nbsp;"
							+ "<a title='Linked-Data-Quelle zu \"%s\" anzeigen' href='%s'>"
							+ "<i class='glyphicon glyphicon-link' aria-hidden='true'></i></a>",
					field, i, field, label, search, label, label, link);
		}
		return withDefaultHidden(field, size, i, result);
	}

	private String withDefaultHidden(String field, int size, int i, String result) {
		if (i == SHORTEN) {
			result = String.format("<span id='%s-hide-by-default' style='display: none;'>", field.replace(".id", ""))
					+ result;
		}
		if (i >= SHORTEN && i == size - 1) {
			result = result + "</span>";
		}
		return result;
	}

}
